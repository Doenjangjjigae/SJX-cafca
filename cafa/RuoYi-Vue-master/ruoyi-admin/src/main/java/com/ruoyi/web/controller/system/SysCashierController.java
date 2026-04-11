package com.ruoyi.web.controller.system;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.service.*;
import com.ruoyi.common.core.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/system/cashier")
public class SysCashierController extends BaseController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ISysMemberService memberService;

    @Autowired
    private ISysMemberCouponService memberCouponService;

    @Autowired
    private ISysOrderService orderService;

    @Autowired
    private ISysOrderCouponService orderCouponService;

    @Autowired
    private ISysOrderItemService orderItemService;

    @Autowired
    private ISysOrderPackageItemService orderPackageItemService;

    @Autowired
    private ISysPointConfigService pointConfigService;

    @Autowired
    private ISysMemberPointsService memberPointsService;

    @Autowired
    private ISysMemberConsumeService memberConsumeService;

    @Autowired
    private ISysProductService productService;

    @Autowired
    private ISysProductMaterialService productMaterialService;

    @Autowired
    private ISysMaterialService materialService;

    @Autowired
    private ISysCouponService couponService;

    @Autowired
    private ISysPackageService packageService;

    @GetMapping("/member/info/{phone}")
    public AjaxResult getMemberByPhone(@PathVariable("phone") String phone) {
        SysMember member = memberService.selectMemberByPhone(phone);
        if (member == null) {
            return success(null);
        }
        return success(member);
    }

    @GetMapping("/member/coupons/{memberId}")
    public AjaxResult getMemberCoupons(@PathVariable("memberId") Long memberId) {
        SysCoupon couponQuery = new SysCoupon();
        couponQuery.setStatus("0");
        List<SysCoupon> allActiveCoupons = couponService.selectSysCouponList(couponQuery);
        
        for (SysCoupon coupon : allActiveCoupons) {
            SysMemberCoupon existingQuery = new SysMemberCoupon();
            existingQuery.setMemberId(memberId);
            existingQuery.setCouponId(coupon.getCouponId());
            List<SysMemberCoupon> existing = memberCouponService.selectMemberCouponList(existingQuery);
            
            if (existing == null || existing.isEmpty()) {
                SysMemberCoupon newMemberCoupon = new SysMemberCoupon();
                newMemberCoupon.setMemberId(memberId);
                newMemberCoupon.setCouponId(coupon.getCouponId());
                newMemberCoupon.setReceiveTime(new Date());
                newMemberCoupon.setUseStatus("0");
                memberCouponService.insertMemberCoupon(newMemberCoupon);
            }
        }
        
        SysMemberCoupon memberCoupon = new SysMemberCoupon();
        memberCoupon.setMemberId(memberId);
        memberCoupon.setUseStatus("0");
        List<SysMemberCoupon> coupons = memberCouponService.selectMemberCouponList(memberCoupon);
        return success(coupons);
    }

    @PostMapping("/calculate")
    public AjaxResult calculateDiscount(@RequestBody CashierRequest request) {
        BigDecimal totalAmount = request.getTotalAmount();
        BigDecimal discountAmount = BigDecimal.ZERO;
        BigDecimal actualAmount = totalAmount;

        if (request.getMemberId() != null) {
            SysMember member = memberService.selectMemberById(request.getMemberId());
            if (member != null && member.getDiscountRate() != null) {
                BigDecimal memberDiscount = totalAmount.multiply(BigDecimal.ONE.subtract(member.getDiscountRate()));
                discountAmount = discountAmount.add(memberDiscount);
                actualAmount = actualAmount.subtract(memberDiscount);
            }
        }

        if (request.getCouponId() != null) {
            SysMemberCoupon memberCoupon = memberCouponService.selectMemberCouponById(request.getCouponId());
            if (memberCoupon != null) {
                SysCoupon coupon = memberCoupon.getCoupon();
                if (coupon != null && "0".equals(coupon.getStatus())) {
                    if (actualAmount.compareTo(coupon.getMinAmount()) >= 0) {
                        BigDecimal couponDiscount;
                        if ("1".equals(coupon.getCouponType())) {
                            couponDiscount = coupon.getDiscountValue();
                            if (couponDiscount.compareTo(actualAmount) > 0) {
                                couponDiscount = actualAmount;
                            }
                        } else {
                            couponDiscount = actualAmount.multiply(BigDecimal.ONE.subtract(coupon.getDiscountValue()));
                        }
                        discountAmount = discountAmount.add(couponDiscount);
                        actualAmount = actualAmount.subtract(couponDiscount);
                    }
                }
            }
        }

        if (request.getMemberId() != null && request.getUsePoints() != null && request.getUsePoints() > 0) {
            SysMember member = memberService.selectMemberById(request.getMemberId());
            if (member != null) {
                if (member.getTotalPoints() != null && member.getTotalPoints() >= request.getUsePoints()) {
                    BigDecimal pointDiscount = new BigDecimal(request.getUsePoints()).multiply(new BigDecimal("0.01"));
                    if (pointDiscount.compareTo(actualAmount) > 0) {
                        pointDiscount = actualAmount;
                    }
                    discountAmount = discountAmount.add(pointDiscount);
                    actualAmount = actualAmount.subtract(pointDiscount);
                }
            }
        }

        if (actualAmount.compareTo(BigDecimal.ZERO) < 0) {
            actualAmount = BigDecimal.ZERO;
        }

        int pointsToEarn = actualAmount.intValue();

        int pointsAfterCheckout = 0;
        if (request.getMemberId() != null) {
            SysMember member = memberService.selectMemberById(request.getMemberId());
            if (member != null && member.getTotalPoints() != null) {
                pointsAfterCheckout = member.getTotalPoints() - (request.getUsePoints() != null ? request.getUsePoints() : 0) + pointsToEarn;
            }
        }

        CashierResponse response = new CashierResponse();
        response.setTotalAmount(totalAmount);
        response.setDiscountAmount(discountAmount);
        response.setActualAmount(actualAmount);
        response.setPointsToEarn(pointsToEarn);
        response.setPointsAfterCheckout(pointsAfterCheckout);

        return success(response);
    }

    @PostMapping("/checkout")
    @Transactional
    public AjaxResult checkout(@RequestBody CheckoutRequest request) {
        try {
            SysOrder order = new SysOrder();
            order.setOrderNumber(generateOrderNumber());
            order.setTableId(request.getTableId());
            order.setTableName(request.getTableName());
            order.setCustomerName(request.getCustomerName());
            order.setCustomerPhone(request.getCustomerPhone());
            order.setMemberId(request.getMemberId());
            order.setTotalAmount(request.getTotalAmount());
            order.setDiscountAmount(request.getDiscountAmount());
            order.setActualAmount(request.getActualAmount());
            order.setUsePoints(request.getUsePoints());
            order.setStatus("1");
            order.setCreateBy(getUsername());
            order.setUpdateBy(getUsername());
            order.setCreateTime(new Date());
            order.setUpdateTime(new Date());

            int result = orderService.insertOrder(order);
            if (result > 0) {
                if (request.getOrderItems() != null && !request.getOrderItems().isEmpty()) {
                    for (SysOrderItem item : request.getOrderItems()) {
                        item.setOrderId(order.getOrderId());
                        item.setCreateTime(new Date());
                        if (item.getSelectedOptions() != null && !item.getSelectedOptions().isEmpty()) {
                            item.setMaterialOptions(objectMapper.writeValueAsString(item.getSelectedOptions()));
                        }
                        if (item.getConfigOptionList() != null && !item.getConfigOptionList().isEmpty()) {
                            item.setConfigOptions(objectMapper.writeValueAsString(item.getConfigOptionList()));
                        }
                        orderItemService.insertOrderItem(item);

                        deductMaterialStock(item);
                    }
                }

                if (request.getOrderPackageItems() != null && !request.getOrderPackageItems().isEmpty()) {
                    for (SysOrderPackageItem item : request.getOrderPackageItems()) {
                        item.setOrderId(order.getOrderId());
                        item.setCreateTime(new Date());
                        orderPackageItemService.insertOrderPackageItem(item);
                        
                        deductPackageMaterialStock(item);
                    }
                }

                if (request.getCouponId() != null) {
                    SysMemberCoupon memberCoupon = memberCouponService.selectMemberCouponById(request.getCouponId());
                    if (memberCoupon != null) {
                        memberCoupon.setUseStatus("1");
                        memberCoupon.setUseTime(new Date());
                        memberCouponService.updateMemberCoupon(memberCoupon);

                        SysOrderCoupon orderCoupon = new SysOrderCoupon();
                        orderCoupon.setOrderId(order.getOrderId());
                        orderCoupon.setCouponId(memberCoupon.getCouponId());
                        orderCoupon.setDiscountAmount(request.getDiscountAmount());
                        orderCoupon.setCreateTime(new Date());
                        orderCouponService.insertOrderCoupon(orderCoupon);
                    }
                }

                if (request.getMemberId() != null && request.getUsePoints() != null && request.getUsePoints() > 0) {
                    memberService.updateMemberPoints(request.getMemberId(), -request.getUsePoints());
                    
                    SysMember member = memberService.selectMemberById(request.getMemberId());
                    SysMemberPoints pointsRecord = new SysMemberPoints();
                    pointsRecord.setMemberId(request.getMemberId());
                    pointsRecord.setMemberCode(member != null ? member.getMemberCode() : "");
                    pointsRecord.setPoints(-request.getUsePoints());
                    pointsRecord.setType("2");
                    pointsRecord.setRemark("订单[" + order.getOrderNumber() + "]积分抵现");
                    pointsRecord.setCreateBy(getUsername());
                    pointsRecord.setCreateTime(new Date());
                    memberPointsService.insertSysMemberPoints(pointsRecord);
                }

                if (request.getMemberId() != null) {
                    int pointsToAdd = request.getActualAmount().intValue();
                    if (pointsToAdd > 0) {
                        memberService.updateMemberPoints(request.getMemberId(), pointsToAdd);
                        
                        SysMember member = memberService.selectMemberById(request.getMemberId());
                        SysMemberPoints pointsRecord = new SysMemberPoints();
                        pointsRecord.setMemberId(request.getMemberId());
                        pointsRecord.setMemberCode(member != null ? member.getMemberCode() : "");
                        pointsRecord.setPoints(pointsToAdd);
                        pointsRecord.setType("0");
                        pointsRecord.setRemark("订单[" + order.getOrderNumber() + "]消费获得积分");
                        pointsRecord.setCreateBy(getUsername());
                        pointsRecord.setCreateTime(new Date());
                        memberPointsService.insertSysMemberPoints(pointsRecord);
                    }
                    
                    SysMember member = memberService.selectMemberById(request.getMemberId());
                    SysMemberConsume consumeRecord = new SysMemberConsume();
                    consumeRecord.setMemberId(request.getMemberId());
                    consumeRecord.setMemberCode(member != null ? member.getMemberCode() : "");
                    consumeRecord.setOrderId(order.getOrderId());
                    consumeRecord.setOrderNumber(order.getOrderNumber());
                    consumeRecord.setAmount(request.getActualAmount());
                    consumeRecord.setPoints(request.getActualAmount().intValue());
                    consumeRecord.setStatus("0");
                    consumeRecord.setRemark("订单消费");
                    consumeRecord.setCreateBy(getUsername());
                    consumeRecord.setCreateTime(new Date());
                    memberConsumeService.insertSysMemberConsume(consumeRecord);
                }

                return success(order);
            }

            return error("收银失败");
        } catch (Exception e) {
            throw new RuntimeException("收银失败：" + e.getMessage(), e);
        }
    }

    private void deductMaterialStock(SysOrderItem item) {
        SysProduct product = productService.selectProductWithMaterials(item.getProductId());
        if (product == null) {
            return;
        }

        Map<Long, BigDecimal> materialQuantityMap = new HashMap<>();

        if (product.getDefaultMaterials() != null) {
            for (SysProductDefaultMaterial material : product.getDefaultMaterials()) {
                Long materialId = material.getMaterialId();
                BigDecimal quantity = material.getQuantity();
                if (materialQuantityMap.containsKey(materialId)) {
                    materialQuantityMap.put(materialId, materialQuantityMap.get(materialId).add(quantity));
                } else {
                    materialQuantityMap.put(materialId, quantity);
                }
            }
        }

        if (item.getConfigOptionList() != null) {
            for (OrderItemConfigOption configOption : item.getConfigOptionList()) {
                if (configOption.getConfigOptionId() != null) {
                    List<SysProductConfigMaterial> configMaterials = 
                        productMaterialService.selectMaterialsByConfigOptionId(configOption.getConfigOptionId());
                    for (SysProductConfigMaterial material : configMaterials) {
                        Long materialId = material.getMaterialId();
                        BigDecimal quantity = material.getQuantity();
                        if (configOption.getQuantity() != null && configOption.getQuantity() > 1) {
                            quantity = quantity.multiply(new BigDecimal(configOption.getQuantity()));
                        }
                        if (materialQuantityMap.containsKey(materialId)) {
                            materialQuantityMap.put(materialId, materialQuantityMap.get(materialId).add(quantity));
                        } else {
                            materialQuantityMap.put(materialId, quantity);
                        }
                    }
                }
            }
        }

        if (product.getMaterials() != null) {
            for (SysProductMaterial material : product.getMaterials()) {
                BigDecimal quantity = material.getQuantity();
                
                if ("1".equals(material.getIsAdjustable()) && item.getSelectedOptions() != null) {
                    for (OrderItemMaterialOption option : item.getSelectedOptions()) {
                        if (option.getProductMaterialId() != null && 
                            option.getProductMaterialId().equals(material.getId())) {
                            quantity = option.getQuantity();
                            break;
                        }
                    }
                }
                
                Long materialId = material.getMaterialId();
                if (materialQuantityMap.containsKey(materialId)) {
                    materialQuantityMap.put(materialId, materialQuantityMap.get(materialId).add(quantity));
                } else {
                    materialQuantityMap.put(materialId, quantity);
                }
            }
        }

        for (Map.Entry<Long, BigDecimal> entry : materialQuantityMap.entrySet()) {
            Long materialId = entry.getKey();
            BigDecimal totalQuantity = entry.getValue().multiply(new BigDecimal(item.getQuantity()));
            
            materialService.deductStockWithCheck(
                materialId, 
                totalQuantity, 
                getUsername(), 
                "订单消费扣减，商品：" + item.getProductName() + "，数量：" + item.getQuantity()
            );
        }
    }

    private void deductPackageMaterialStock(SysOrderPackageItem packageItem) {
        SysPackage pkg = packageService.selectPackageById(packageItem.getPackageId());
        if (pkg == null || pkg.getItems() == null) {
            return;
        }

        for (SysPackageItem item : pkg.getItems()) {
            if (item.getProductId() != null) {
                SysOrderItem tempItem = new SysOrderItem();
                tempItem.setProductId(item.getProductId());
                tempItem.setProductName(item.getProductName());
                tempItem.setQuantity(packageItem.getQuantity() * item.getQuantity());
                
                if (packageItem.getItemConfigs() != null) {
                    for (PackageItemConfig config : packageItem.getItemConfigs()) {
                        if (config.getProductId() != null && config.getProductId().equals(item.getProductId())) {
                            tempItem.setConfigOptionList(config.getConfigOptionList());
                            break;
                        }
                    }
                }
                
                deductMaterialStock(tempItem);
            }
        }
    }

    private String generateOrderNumber() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String random = String.valueOf((int) (Math.random() * 1000));
        return "SJX" + timestamp + random;
    }

    public static class CashierRequest {
        private Long memberId;
        private Long couponId;
        private BigDecimal totalAmount;
        private Integer usePoints;

        public Long getMemberId() {
            return memberId;
        }

        public void setMemberId(Long memberId) {
            this.memberId = memberId;
        }

        public Long getCouponId() {
            return couponId;
        }

        public void setCouponId(Long couponId) {
            this.couponId = couponId;
        }

        public BigDecimal getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(BigDecimal totalAmount) {
            this.totalAmount = totalAmount;
        }

        public Integer getUsePoints() {
            return usePoints;
        }

        public void setUsePoints(Integer usePoints) {
            this.usePoints = usePoints;
        }
    }

    public static class CashierResponse {
        private BigDecimal totalAmount;
        private BigDecimal discountAmount;
        private BigDecimal actualAmount;
        private Integer pointsToEarn;
        private Integer pointsAfterCheckout;

        public BigDecimal getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(BigDecimal totalAmount) {
            this.totalAmount = totalAmount;
        }

        public BigDecimal getDiscountAmount() {
            return discountAmount;
        }

        public void setDiscountAmount(BigDecimal discountAmount) {
            this.discountAmount = discountAmount;
        }

        public BigDecimal getActualAmount() {
            return actualAmount;
        }

        public void setActualAmount(BigDecimal actualAmount) {
            this.actualAmount = actualAmount;
        }

        public Integer getPointsToEarn() {
            return pointsToEarn;
        }

        public void setPointsToEarn(Integer pointsToEarn) {
            this.pointsToEarn = pointsToEarn;
        }

        public Integer getPointsAfterCheckout() {
            return pointsAfterCheckout;
        }

        public void setPointsAfterCheckout(Integer pointsAfterCheckout) {
            this.pointsAfterCheckout = pointsAfterCheckout;
        }
    }

    public static class CheckoutRequest {
        private Long tableId;
        private String tableName;
        private String customerName;
        private String customerPhone;
        private Long memberId;
        private Long couponId;
        private BigDecimal totalAmount;
        private BigDecimal discountAmount;
        private BigDecimal actualAmount;
        private Integer usePoints;
        private List<SysOrderItem> orderItems;
        private List<SysOrderPackageItem> orderPackageItems;

        public Long getTableId() {
            return tableId;
        }

        public void setTableId(Long tableId) {
            this.tableId = tableId;
        }

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getCustomerPhone() {
            return customerPhone;
        }

        public void setCustomerPhone(String customerPhone) {
            this.customerPhone = customerPhone;
        }

        public Long getMemberId() {
            return memberId;
        }

        public void setMemberId(Long memberId) {
            this.memberId = memberId;
        }

        public Long getCouponId() {
            return couponId;
        }

        public void setCouponId(Long couponId) {
            this.couponId = couponId;
        }

        public BigDecimal getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(BigDecimal totalAmount) {
            this.totalAmount = totalAmount;
        }

        public BigDecimal getDiscountAmount() {
            return discountAmount;
        }

        public void setDiscountAmount(BigDecimal discountAmount) {
            this.discountAmount = discountAmount;
        }

        public BigDecimal getActualAmount() {
            return actualAmount;
        }

        public void setActualAmount(BigDecimal actualAmount) {
            this.actualAmount = actualAmount;
        }

        public Integer getUsePoints() {
            return usePoints;
        }

        public void setUsePoints(Integer usePoints) {
            this.usePoints = usePoints;
        }

        public List<SysOrderItem> getOrderItems() {
            return orderItems;
        }

        public void setOrderItems(List<SysOrderItem> orderItems) {
            this.orderItems = orderItems;
        }

        public List<SysOrderPackageItem> getOrderPackageItems() {
            return orderPackageItems;
        }

        public void setOrderPackageItems(List<SysOrderPackageItem> orderPackageItems) {
            this.orderPackageItems = orderPackageItems;
        }
    }
}
