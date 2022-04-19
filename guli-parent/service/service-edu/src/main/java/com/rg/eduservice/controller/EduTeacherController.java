package com.rg.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rg.commonutils.R;
import com.rg.eduservice.entity.EduTeacher;
import com.rg.eduservice.entity.vo.TeacherQuery;
import com.rg.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.List;


/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author lxy
 * @since 2022-02-05
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
@Slf4j
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    //1.查询讲师所有数据  使用Rest风格..
    @ApiOperation("所有讲师列表")
    @GetMapping("findAll")
    public R findAll() {
        List <EduTeacher> eduTeacherList = eduTeacherService.list(null);
        return R.ok().data("items", eduTeacherList);
    }

    //2.通过id逻辑删除讲师
    @ApiOperation("根据ID删除讲师")
    @DeleteMapping("removeById/{id}")
    @CacheEvict(value = "teacher", allEntries=true)
    public R removeById(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id) {
        boolean flag = eduTeacherService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //3.分页查询讲师
    @ApiOperation("分页查询讲师")
    @GetMapping("pageList/{current}/{limit}")
    public R pageList(@ApiParam(name = "current", value = "当前页码", required = true)
                        @PathVariable("current") Integer current,
                        @ApiParam(name = "limit", value = "每页记录数", required = true)
                        @PathVariable("limit") Integer limit) {
        Page <EduTeacher> page = new Page <>(current, limit);

        //进行分页查询
        eduTeacherService.page(page, null);//最后这些属性既可以通过page也可以通过iPage获取,因为分页后的数据会被重新封装到这些对象里面.

        return R.ok().data("total", page.getTotal()).data("rows", page.getRecords());
    }

    //4.根据条件查询讲师
    @ApiOperation("根据条件查询讲师")
    @PostMapping("pageQuery/{current}/{limit}") //@RequestBody: 通过json传递数据,把数据封装到对象中. json数据只能封装到请求体中,所以就需要用到POST请求
    public R pageQuery(@ApiParam(name = "current", value = "当前页码", required = true)
                       @PathVariable("current") Integer current,
                       @ApiParam(name = "limit", value = "每页记录数", required = true)
                       @PathVariable("limit") Integer limit,
                       @ApiParam(name = "teacherQuery",value = "查询条件",required = false) @RequestBody(required = false) TeacherQuery teacherQuery){//required = false,表示条件可有可无
        Page <EduTeacher> page = eduTeacherService.getEduTeacherPage(current, limit, teacherQuery);
        return R.ok().data("total", page.getTotal()).data("rows", page.getRecords());
    }

    //5.添加讲师
    @ApiOperation("添加讲师")
    @PostMapping("addTeacher")
    @CacheEvict(value = "teacher", allEntries=true)
    public R addTeacher(@ApiParam(name = "eduTeacher",value = "讲师信息",required = true) @RequestBody EduTeacher eduTeacher){
        boolean flag = eduTeacherService.save(eduTeacher);
        if (flag){
            return R.ok();
        }else{
            return R.error();
        }
    }

    //6.根据讲师Id进行查询-->数据回显功能
    @GetMapping("getById/{id}")
    @ApiOperation("根据id查询讲师")
    public R getById(@ApiParam(name = "id",value = "讲师ID",required = true) @PathVariable String id){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("teacher", eduTeacher);
    }

    //7.讲师修改功能
    @ApiOperation("修改讲师")
    @PutMapping("updateById")
    @CacheEvict(value = "teacher", allEntries=true)
    public R updateById(@ApiParam(name = "eduTeacher",value = "讲师信息",required = true) @RequestBody EduTeacher eduTeacher){
        boolean flag = eduTeacherService.updateById(eduTeacher);
        if(flag){
            return R.ok();
        }else{
            return R.error();
        }
    }

}

