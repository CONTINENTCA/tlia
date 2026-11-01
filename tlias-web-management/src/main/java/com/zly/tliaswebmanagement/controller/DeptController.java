package com.zly.tliaswebmanagement.controller;

import com.zly.tliaswebmanagement.anno.LogOperation;
import com.zly.tliaswebmanagement.pojo.Dept;
import com.zly.tliaswebmanagement.pojo.Result;
import com.zly.tliaswebmanagement.service.DeptService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.List;
// http://localhost:90/dept
@Slf4j // 这个注解相当于在类中定义了一个private static final Logger log = LoggerFactory.getLogger(DeptController.class);
@RestController
public class DeptController {

    //private static final  Logger log = LoggerFactory.getLogger(DeptController.class);

    @Autowired
    private DeptService deptService;

    // 1 查询员工
    //@RequestMapping(value = "/depts",method = RequestMethod.GET) // 这个method参数是限定请求方法
    @GetMapping("/depts") // 相当于封装了上面的注解
    public Result list(){
        //System.out.println("查询全部部门数据");
        log.info("查询全部部门数据");
        List<Dept> deptList = deptService.findAll();
        return Result.success(deptList);
    }

    // 2 根据id删除
    // 方式一:通过原始的HttpServletRequest对象获取请求参数(不推荐)
    /*
    @DeleteMapping("/depts")
    public Result delete(HttpServletRequest request){
        String idStr = request.getParameter("id");
        int id = Integer.parseInt(idStr);
        System.out.println("根据ID删除部门:"+id);
        return Result.success();
    }
     */

    // 方式2：通过Spring提供的@RequestParam注解 将请求参数绑定给方法形参
    // 注意事项：一旦声明了@RequestParam 该参数在请求时必须传递
    // 如果不传递将会报错（默认 required为true)
    @LogOperation
    @DeleteMapping("/depts") // 这个请求参数是url中?传递过来的
    public Result delete(@RequestParam(value = "id",required = true) Integer deptId){ //这个id是前端传来的 required参数默认是true 也可以不写
        //System.out.println("根据ID删除部门:"+deptId);
        log.info("根据部门ID删除部门:" + deptId);
        deptService.deleteById(deptId);
        return Result.success();
    }

    // 方式3：如果请求参数名与形参变量名相同 那么可以省略@RequestParam
    /*
    @DeleteMapping("/depts")
    public Result delete(Integer id){
        System.out.println("根据ID删除部门:"+id);
        return Result.success();
    }
     */

    // 3 添加部门
    // 前端传递过来的参数是从请求体中传递过来的 json格式
    /*
    {
        "name":"教研部"
    }
    JSON格式的参数 通常会使用一个实体对象进行接受
    规则：JSON数据的键名与方法形参对象的属性名相同
    并需要@RequestBody注解标识
     */
    @LogOperation
    @PostMapping("/depts")
    public Result add(@RequestBody Dept dept){
        //System.out.println("添加部门:" + dept);
        log.info("新增部门:" + dept);
        deptService.add(dept);
        return Result.success();
    }


    // 查询回显
    // 路径参数：通过请求URL直接传递参数 使用{...}来标识该路径参数 需要使用@PathVariable获取路径参数
    @GetMapping("/depts/{id}")
    public Result getInfo(@PathVariable("id") Integer deptId){ // 如果把deptId替换成id 那么@PathVariable("id")中("id")可以省略
        //System.out.println("查询部门id:"+deptId);
        log.info("查询部门ID:" + deptId);
        Dept dept = deptService.getInfo(deptId);
        return Result.success(dept);
    }

    // 修改部门数据
    /*
    请求参数样例：
    {
        "id":1,
        "name":"教研部"
    }
     */
    @LogOperation
    @PutMapping("/depts")
    public Result update(@RequestBody Dept dept){
        //System.out.println("修改部门:" + dept);
        log.info("修改部门:" + dept);
        deptService.update(dept);
        return Result.success();
    }

}
