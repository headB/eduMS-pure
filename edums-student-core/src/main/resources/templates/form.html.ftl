<title>${entity?uncap_first} form</title>

<form class="layui-form" lay-filter="layadmin-${entity?uncap_first}-formlist">
    <#list table.commonFields as field>
        <#if field.keyFlag><#-- 生成 id 回显 -->
    <input type="hidden" name="${field.propertyName}">
        </#if>
    </#list>
    <#list table.fields as field>
        <#if !field.keyFlag><#-- 生成普通字段 -->
    <div class="layui-form-item">
        <label class="layui-form-label">${field.propertyName}</label>
        <div class="layui-input-block">
            <input type="text" name="${field.propertyName}" placeholder="请输入" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
        </#if>
    </#list>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="LAY-${entity?uncap_first}-front-submit">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>