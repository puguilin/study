package com.guilin.studycode.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guilin.studycode.entrity.Student;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.*;

import java.util.Map;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {

    @Select("select * from student where ID =#{ID}")
    Student queryById(int ID);


 /*   @Insert("insert into student(ID,SNO,SNAME,SSEX)values" +
            "(#{ID},#{SNO},#{SNAME},#{SSEX})")
    int insert(Student student);*/


    @SelectProvider(type = Query.class, method = "pageQuery")
    IPage<Student> pageQuery(IPage<Student> result, @Param("ssex") String ssex,@Param("sname") String sname);

    public class Query {

        public String pageQuery(Map params) {
            StringBuilder stringBuilder = new StringBuilder(
                    "SELECT * FROM student where 1=1 ");
            String ssex = (String) params.get("ssex");
            String sname = (String) params.get("sname");

            if (StringUtils.isNotBlank(ssex)) {
                stringBuilder.append(" and ssex =#{ssex}");
            }
            if (StringUtils.isNotBlank(sname)) {
                stringBuilder.append(" and sname like concat('%', #{sname}, '%')");
            }
            return stringBuilder.toString();
        }

    }

    @Insert("insert into student(SNO,SNAME,SSEX,createDate,remark)values" +
            "(#{SNO},#{SNAME},#{SSEX},#{createDate},#{remark})")
    public int saveStudentMap(Map<String, String> map);


}
