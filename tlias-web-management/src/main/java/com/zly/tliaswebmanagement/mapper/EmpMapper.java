package com.zly.tliaswebmanagement.mapper;

import com.zly.tliaswebmanagement.pojo.Emp;
import com.zly.tliaswebmanagement.pojo.EmpQueryParam;
import com.zly.tliaswebmanagement.pojo.GenderCount;
import com.zly.tliaswebmanagement.pojo.JobCount;
import lombok.Data;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
员工信息
 */
@Mapper
public interface EmpMapper {

    /*
    // 传统分页查询
    // 查询总记录数
    @Select("select count(*) from tlias.emp left join tlias.dept on emp.dept_id = dept.id")
    public Long count();

    // 分页查询
    @Select("select emp.id,emp.username,emp.password,emp.name,emp.gender,emp.phone," +
            "emp.job,emp.salary,emp.image,emp.entry_date as entryDate," +
            "emp.dept_id as deptId,emp.create_time as createTime," +
            "emp.update_time as updateTime,dept.name as deptName" +
            " from tlias.emp left join tlias.dept on emp.dept_id = dept.id" +
            " order by emp.update_time desc limit #{start},#{pageSize}")
    public List<Emp> list(@Param("start") int start,@Param("pageSize") int pageSize);
     */


    // 我们用传统的方式来优化sql 致使我们可以在选择条件查询时 传入部分的参数 来达到筛选效果
    @Select("select emp.id,emp.username,emp.password,emp.name,emp.gender,emp.phone," +
            "emp.job,emp.salary,emp.image,emp.entry_date as entryDate," +
            "emp.dept_id as deptId,emp.create_time as createTime," +
            "emp.update_time as updateTime,dept.name as deptName " +
            "from tlias.emp left join tlias.dept on emp.dept_id = dept.id " +
            "where emp.name like concat('%',#{p.name},'%') " +
            "and ( IFNULL(#{p.gender}, 3) = 3 or emp.gender = #{p.gender} ) " +
            "and ( (#{p.begin} is null or #{p.end} is null) or emp.entry_date between #{p.begin} and #{p.end} ) " +
            "order by emp.update_time desc")
    public List<Emp> list_use_pageHelper(@Param("p") EmpQueryParam param);


    // 也可以用 动态sql来解决上面的问题
    /*
    <if>:判断条件是否成立 如果条件为true 则拼接sql
    <where>:根据查询条件 来生产where关键字 并会自动去除条件前面多余的and或or
     */
    // 在xml配置文件
    public List<Emp> list_use_pageHelper_dynamic(@Param("p") EmpQueryParam param);

    // 在xml配置文件
    /*
    = 让 MyBatis 把数据库自动生成的主键 ID，回填到 Java 对象里
    二、两个属性分别干什么？
    1. useGeneratedKeys = true
    告诉 MyBatis：
    “这个表的主键是数据库自动递增的，执行 insert 后，帮我把生成的 ID 取回来！”
    2. keyProperty = "id"
    告诉 MyBatis：
    “取回来的 ID，赋值给我传入的 Emp 对象的 id 字段！”
     */
    @Options(useGeneratedKeys = true,keyProperty = "p.id")
    public void insert(@Param("p") Emp param);

    // 批量删除员工
    public void delete(@Param("ids") List<Integer> ids);

    // 修改员工查询回显
    public Emp getById(@Param("id") Integer id);

    // 修改emp表的基本信息
    public void updateById(@Param("e") Emp emp);

    // 统计员工职位人数
    /*
    @MapKey 核心作用（一句话）
    把查询出来的多条数据，自动拼成一个 Map<key字段, 整条数据对象>

    // 外层是一个大Map，key=pos字段值，value=单条数据实体/Map
    @MapKey("pos")
    Map<Integer, Map<String,Object>> countEmpJobData();

    执行后效果：
    原本列表：
    [
     {pos:1, count:10},
     {pos:2, count:20}
    ]
    自动变成：
    {
      1: {pos:1, count:10},
      2: {pos:2, count:20}
    }

    Map<Integer, Map<String,Object>> countEmpJobData();
    如果不加 @MapKey("pos") → 直接报错！运行失败！
    加了 @MapKey("pos") → 才能返回 Map！
    我用最简单的 3 句话讲死理：
    1. 数据库查出来 → 永远是 多行数据（List）
    plaintext
    行1：pos=1, count=20
    行2：pos=2, count=15
    行3：pos=3, count=8
    2. MyBatis 默认只能返回 List<...>
    你告诉它返回 Map，它不知道用哪个字段当 KEY！它会懵掉 → 报错！
    3. @MapKey("pos") 就是告诉 MyBatis：
    用 pos 字段的值当作 Map 的 key！

     */
    // ❌ 返回值是 List，@MapKey 根本不生效！
    // @MapKey 只生效于：方法返回外层是 Map
    //@MapKey("pos") // 如果不加这个注解 那么mybatis的插件(小鸟)会报错（误报） 这个注解是用来指定谁是key的
    public List<Map<String,Object>> countEmpJobData(); // 第二个泛型只能写Object
    /*
    一行数据 = 一个 Map
    多行数据 = List 装着多个 Map
    你马上就彻底懂了！
    假设你数据库查出来 2 行数据
    表格
    pos (职位)	num (人数)
    班主任	    5
    讲师	        8
    1. MyBatis 做的事情：一行 → 包成一个 Map
    第一行数据
    pos = 班主任
    num = 5
    MyBatis 自动把它变成：
    Map<String, Object> row1 = new HashMap<>();
    row1.put("pos", "班主任");  // 字段名 → key，值 → value
    row1.put("num", 5);
    第二行数据
    pos = 讲师
    num = 8
    MyBatis 自动变成：
    Map<String, Object> row2 = new HashMap<>();
    row2.put("pos", "讲师");
    row2.put("num", 8);
    2. 然后把这两个 Map 装进一个 List
    List<Map<String, Object>> list = new ArrayList<>();
    list.add(row1);
    list.add(row2);
    3. 最终你拿到的结构就是：
    [
      { "pos":"班主任", "num":5 },
      { "pos":"讲师",    "num":8 }
    ]
    这就是：
    List<Map<String,Object>>
    🔥 终极大白话总结
    查询返回多少行，MyBatis 就给你创建多少个 Map！
    一行 = 一个 Map
    列名 = Map 的 key
    单元格的值 = Map 的 value
     */

    // 不使用Map
    // 返回 List<你的实体类>，不再用 Map！
    public List<JobCount> countEmpJobData_no_fucking_map();

    //@MapKey("name") // 依旧误报 不用管他
    public List<Map<String, Object>> countEmpGenderData();

    public List<GenderCount> countEmpGenderData_no_fucking_map();

    /**
     * 根据员工用户名和密码查员工信息
     */
    public Emp selectByUsernameAndPassword(@Param("emp") Emp emp);
}
