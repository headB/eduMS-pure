/** layuiAdmin.pro-v1.2.1 LPPL License By http://www.layui.com/admin/ */
;layui.define(["table", "form", "layer"], function (e) {
    var $ = layui.$, admin = layui.admin, layer = layui.layer, view = layui.view, table = layui.table,
        form = layui.form,
        token = layui.data(layui.setter.tableName)[layui.setter.request.tokenName];
    table.render({
        elem: "#LAY-trainsubject-manage",
        url: "/trainsubjects",
        headers: {'x-auth-token': token},
        request: {pageName: 'current', limitName: 'size'},
        cols: [[
            {type: "checkbox", fixed: "left"},
            {field: "name", title: "name"},
            {field: "intro", title: "intro"},
            {field: "beginTime", title: "begintime"},
            {field: "endTime", title: "endtime"},
            {field: "status", title: "status"},
            {field: "deptId", title: "deptId"},
            {field: "leaderId", title: "leaderId"},
            {field: "otherLeaderId", title: "otherleaderId"},
            {title: "操作", width: 150, align: "center", fixed: "right", toolbar: "#trainsubject-list-manage-btn"}
        ]],
        toolbar: '#trainsubject-table-manage-btn',
        defaultToolbar: ['filter', 'exports', {
            title: '提示' //标题
            , layEvent: 'LAYTABLE_TIPS' //事件名，用于 toolbar 事件中使用
            , icon: 'layui-icon-tips' //图标类名
        }],
        page: true,
        limit: 30,
        height: 'full-200',
    }), table.on("tool(LAY-trainsubject-manage)", function (e) {
        var data = e.data;

        switch (e.event) {
            case "del":
                layer.confirm("确定要删除该数据吗？", function (i) {
                    if (e.data.id) {
                        admin.req({
                            url: '/trainsubjects',
                            data: {ids: [e.data.id]},
                            type: 'delete',
                            done: function (res) {
                                layer.msg(res.msg);
                            }
                        });
                    }
                    e.del(), layer.close(i)
                });
                break;
            case  "edit":
                admin.popup({
                    title: "编辑",
                    area: ["500px", "450px"],
                    id: "LAY-popup-trainsubject-add",
                    success: function (e, i) {
                        view(this.id).render("system/trainsubject/form", data).done(function () {
                            // 数据回显
                            form.val("layadmin-trainsubject-formlist", data);
                            // 禁用用户名表单
                            $("input[name=username]").prop("disabled", true).prop("readOnly", true);
                            // 重新渲染页面
                            form.render(null, "layadmin-trainsubject-formlist"),
                                // 绑定提交表单事件
                                form.on("submit(LAY-trainsubject-front-submit)", function (e) {
                                    // 提交数据到后台
                                    admin.req({
                                        url: '/trainsubjects',
                                        type: 'put',
                                        data: e.field,
                                        done: function (e) {
                                            layer.msg(e.msg);
                                            if (e.code === 0) {
                                                setTimeout(function () {
                                                    layui.table.reload("LAY-trainsubject-manage"), layer.close(i)
                                                }, 1000);
                                            }
                                        }
                                    });
                                    return false;
                                })
                        })
                    }
                });
                break;
        }
    }), e("trainsubject", {})
});