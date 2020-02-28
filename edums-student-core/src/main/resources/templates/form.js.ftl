/** layuiAdmin.pro-v1.2.1 LPPL License By http://www.layui.com/admin/ */
;layui.define(["table", "form", "layer"], function (e) {
    var $ = layui.$, admin = layui.admin, layer = layui.layer, view = layui.view, table = layui.table,
        form = layui.form,
        token = layui.data(layui.setter.tableName)[layui.setter.request.tokenName];
    table.render({
        elem: "#LAY-${entity?uncap_first}-manage",
        url: "/${entity?uncap_first}s",
        headers: {'x-auth-token': token},
        request: {pageName: 'current', limitName: 'size'},
        cols: [[
            {type: "checkbox", fixed: "left"},
    <#list table.fields as field>
        <#if !field.keyFlag><#-- 生成普通字段 -->
            {field: "${field.propertyName}", title: "${field.propertyName}"},
        </#if>
    </#list>
            {title: "操作", width: 150, align: "center", fixed: "right", toolbar: "#${entity?uncap_first}-list-manage-btn"}
        ]],
        toolbar: '#${entity?uncap_first}-table-manage-btn',
        defaultToolbar: ['filter', 'exports', {
            title: '提示' //标题
            , layEvent: 'LAYTABLE_TIPS' //事件名，用于 toolbar 事件中使用
            , icon: 'layui-icon-tips' //图标类名
        }],
        page: true,
        limit: 30,
        height: 'full-200',
    }), table.on("toolbar(LAY-${entity?uncap_first}-manage)", function (e) {
        active[e.event](e);
    }), table.on("tool(LAY-${entity?uncap_first}-manage)", function (e) {
        active[e.event](e);
    }), e("${entity?uncap_first}", {});

    //事件
    var active = {
        "batchdel": function (e) {
            var checkStatus = table.checkStatus('LAY-${entity?uncap_first}-manage')
            , checkData = checkStatus.data; //得到选中的数据

            if (checkData.length === 0) {
                return layer.msg('请选择数据');
            }

            layer.confirm('确定删除吗？', function (index) {
                var ids = $.map(checkData, function (v, i) {
                    return v.id
                });
                //执行 Ajax 后重载
                admin.req({
                    url: '/${entity?uncap_first}s',
                    type: 'delete',
                    data: {ids: ids},
                    done: function (e) {
                        layer.msg(e.msg);
                            if (e.code === 0) {
                                setTimeout(function () {
                                    layui.table.reload("LAY-${entity?uncap_first}-manage"), layer.close(index)
                                }, 1000);
                            }
                    }
                });
            });
        }, "add": function (e) {
            admin.popup({
                title: '添加',
                area: ['500px', '650px'],
                id: 'LAY-popup-${entity?uncap_first}-add',
                success: function (layero, index) {
                    view(this.id).render('system/${entity?uncap_first}/form').done(function () {
                        form.render(null, 'layadmin-${entity?uncap_first}-formlist');

                        //监听提交
                        form.on('submit(LAY-${entity?uncap_first}-front-submit)', function (data) {
                            // 提交数据到后台
                            admin.req({
                                url: '/${entity?uncap_first}s',
                                type: 'post',
                                data: data.field,
                                done: function (e) {
                                    layer.msg(e.msg);
                                    if (e.code === 0) {
                                        setTimeout(function () {
                                            layui.table.reload("LAY-${entity?uncap_first}-manage"), layer.close(index)
                                        }, 1000);
                                    }
                                }
                            });

                            return false;
                        });
                    });
                }
            });
        }, "edit": function (e) {
            var data = e.data;

            admin.popup({
                title: "编辑",
                area: ['960px', '480px'],
                id: "LAY-popup-${entity?uncap_first}-add",
                success: function (e, i) {
                    view(this.id).render("system/${entity?uncap_first}/form", data).done(function () {
                        // 数据回显
                        form.val("layadmin-${entity?uncap_first}-formlist", data);
                        // 重新渲染页面
                        form.render(null, "layadmin-${entity?uncap_first}-formlist"),
                        // 绑定提交表单事件
                        form.on("submit(LAY-${entity?uncap_first}-front-submit)", function (e) {
                            // 提交数据到后台
                            admin.req({
                                url: '/${entity?uncap_first}s',
                                type: 'put',
                                data: e.field,
                                done: function (e) {
                                    layer.msg(e.msg);
                                    if (e.code === 0) {
                                        setTimeout(function () {
                                        layui.table.reload("LAY-${entity?uncap_first}-manage"), layer.close(i)
                                    }, 1000);
                                }
                                }
                            });
                            return false;
                        });
                    });
                }
            });
        }, "del": function(e) {
            layer.confirm("确定要删除该数据吗？", function (i) {
                if (e.data.id) {
                    admin.req({
                        url: '/${entity?uncap_first}s',
                        data: {ids: [e.data.id]},
                        type: 'delete',
                        done: function (res) {
                            layer.msg(res.msg);
                        }
                    });
                }
                e.del(), layer.close(i)
            });
        }
    };
});