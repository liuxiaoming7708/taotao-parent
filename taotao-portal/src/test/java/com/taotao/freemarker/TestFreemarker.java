package com.taotao.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.*;

/**
 * freemarker的使用方法
 * Created by Administrator on 2017/4/24.
 */
public class TestFreemarker {
    public class Student{
        private int id;
        private String name;
        private String address;

        public Student(int id,String name,String address){
            this.id = id;
            this.name = name;
            this.address = address;
        }
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    //hello freemarker
    @Test
    public void testFreeMarker() throws Exception{
        // 第一步：把freemarker的jar包添加到工程中
        // 第二步：freemarker的运行不依赖web容器，可以在java工程中运行。创建一个测试方法进行测试。
        // 第三步：创建一个Configuration对象
        Configuration configuration = new Configuration(Configuration.getVersion());
        // 第四步：告诉config对象模板文件存放的路径。
        configuration.setDirectoryForTemplateLoading(new File("D:\\masterSpring\\taotao-parent\\taotao-portal\\src\\main\\webapp\\WEB-INF\\ftl"));
        // 第五步：设置config的默认字符集。一般是utf-8
        configuration.setDefaultEncoding("utf-8");
        // 第六步：从config对象中获得模板对象。需要制定一个模板文件的名字。
        Template template = configuration.getTemplate("first.ftl");
        // 第七步：创建模板需要的数据集。可以是一个map对象也可以是一个pojo，把模板需要的数据都放入数据集。
        Map root = new HashMap<>();

        root.put("hello", "hello freemarker");
        // 第八步：创建一个Writer对象，指定生成的文件保存的路径及文件名。
        Writer out = new FileWriter(new File("D:\\temp\\html\\hello.html"));
        // 第九步：调用模板对象的process方法生成静态文件。需要两个参数数据集和writer对象。
        template.process(root, out);
        // 第十步：关闭writer对象。
        out.flush();
        out.close();
    }

    //访问对象 遍历集合
    @Test
    public void testFreeMarkerSecond() throws Exception{
        // 第一步：把freemarker的jar包添加到工程中
        // 第二步：freemarker的运行不依赖web容器，可以在java工程中运行。创建一个测试方法进行测试。
        // 第三步：创建一个Configuration对象
        Configuration configuration = new Configuration(Configuration.getVersion());
        // 第四步：告诉config对象模板文件存放的路径。
        configuration.setDirectoryForTemplateLoading(new File("D:\\masterSpring\\taotao-parent\\taotao-portal\\src\\main\\webapp\\WEB-INF\\ftl"));
        // 第五步：设置config的默认字符集。一般是utf-8
        configuration.setDefaultEncoding("utf-8");
        // 第六步：从config对象中获得模板对象。需要制定一个模板文件的名字。
        Template template = configuration.getTemplate("second.ftl");
        // 第七步：创建模板需要的数据集。可以是一个map对象也可以是一个pojo，把模板需要的数据都放入数据集。
        Map root = new HashMap<>();
        //root.put("title", "hello freemarker");
        root.put("student",new Student(1,"张三","北京"));
        //展示列表
        List<Student> students = new ArrayList<>();
        students.add(new Student(1,"张三","北京"));
        students.add(new Student(2,"张三2","北京2"));
        students.add(new Student(3,"张三3","北京3"));
        students.add(new Student(4,"张三4","北京4"));
        students.add(new Student(1,"张三5","北京5"));
        root.put("students",students);
        //当前时间
        root.put("cur_date",new Date());
        // 第八步：创建一个Writer对象，指定生成的文件保存的路径及文件名。
        Writer out = new FileWriter(new File("D:\\temp\\html\\second.html"));
        // 第九步：调用模板对象的process方法生成静态文件。需要两个参数数据集和writer对象。
        template.process(root, out);
        // 第十步：关闭writer对象。
        out.flush();
        out.close();
    }

}

