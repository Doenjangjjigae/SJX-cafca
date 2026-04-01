package com.ruoyi.system.controller;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.SysAIModel;
import com.ruoyi.system.service.ISysAIModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/ai/model")
public class SysAIModelController {

    @Autowired
    private ISysAIModelService sysAIModelService;

    @GetMapping("/list")
    public R<List<SysAIModel>> list() {
        List<SysAIModel> list = sysAIModelService.selectAIModelList();
        return R.ok(list);
    }

    @GetMapping("/info/{modelId}")
    public R<SysAIModel> info(@PathVariable Long modelId) {
        SysAIModel model = sysAIModelService.selectAIModelById(modelId);
        return R.ok(model);
    }

    @PostMapping("/add")
    public R<String> add(@RequestBody SysAIModel sysAIModel) {
        int result = sysAIModelService.insertAIModel(sysAIModel);
        return result > 0 ? R.ok("添加成功") : R.fail("添加失败");
    }

    @PutMapping("/edit")
    public R<String> edit(@RequestBody SysAIModel sysAIModel) {
        int result = sysAIModelService.updateAIModel(sysAIModel);
        return result > 0 ? R.ok("修改成功") : R.fail("修改失败");
    }

    @DeleteMapping("/delete/{modelId}")
    public R<String> delete(@PathVariable Long modelId) {
        int result = sysAIModelService.deleteAIModelById(modelId);
        return result > 0 ? R.ok("删除成功") : R.fail("删除失败");
    }

    @PutMapping("/enable/{modelId}")
    public R<String> enable(@PathVariable Long modelId) {
        int result = sysAIModelService.enableAIModel(modelId);
        return result > 0 ? R.ok("启用成功") : R.fail("启用失败");
    }
}