<html>
<head>
    <title>${title!"处理null值：title的默认值"}</title>
</head>
<body>
<#include "first.ftl">
<br/>
<label>学号：</label>${student.id}<br/>
<label>姓名：</label>${student.name}<br/>
<label>住址：</label>${student.address}<br/>


学生列表(包含各行变色判断)：
<table border="1">
    <tr>
    <#--<td>${s_has_next}</td>-->
        <td>下标</td>
        <td>ID</td>
        <td>姓名</td>
        <td>地址</td>
    </tr>
    <#list students as s>
    <#if s_index % 2 == 0>
    <tr style="color: red">
    <#else>
    <tr>
    </#if>

        <#--<td>${s_has_next}</td>-->
        <td>${s_index}</td>
        <td>${s.id}</td>
        <td>${s.name}</td>
        <td>${s.address}</td>
    </tr>
    </#list>
</table>
<br/>


日期几种格式：<br/>
当前日期：${cur_date?date}<br/>
当前时间：${cur_date?time}<br/>
当前日期加时间：${cur_date?datetime}<br/>
当前日期加时间（自定义格式）：${cur_date?string("yyyy年MM月dd日 HH时mm分ss秒")}<br/>
<br/>


判断当前日期是否为null：<br/>
没值：
<#if cur_date_null ??>
${cur_date_null?date}<br/>
<#else>
当前时间属性为null
</#if>
<br/>
有值：
<#if cur_date ??>
${cur_date?date}<br/>
<#else>
当前时间属性为null
</#if>

</body>
</html>
