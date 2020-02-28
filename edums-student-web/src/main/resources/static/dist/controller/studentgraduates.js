/** layuiAdmin.pro-v1.2.1 LPPL License By http://www.layui.com/admin/ */
;layui.define(["table", "layer"], function (e) {
    var $ = layui.$, admin = layui.admin, layer = layui.layer, view = layui.view, table = layui.table,
        token = layui.data(layui.setter.tableName)[layui.setter.request.tokenName];
        table.render({
        elem: "#LAY-studentGraduates-manage",
        url: "/studentGraduatess",
        headers: {'x-auth-token': token},
        request: {pageName: 'current', limitName: 'size'},
        cols: [[
            {type: "checkbox", fixed: "left"},
            {field: "studentName", title: "学员", width: 80},
            {field: "subjectName", title: "所属学科", width: 100},
            {field: "classinfoName", title: "所在班级" ,width: 250},
            {field: "performanceTitle", title: "在校表现",hide:true},
            {field: "graduateDate", title: "毕业时间"},
            {field: "interviewNum", title: "面试次数"},
            {field: "expectCity", title: "期望工作城市",hide:true},
            {field: "mockSalary", title: "模拟面试定薪",hide:true},
            {field: "statusName", title: "就业状态"},
            {field: "workDate", title: "工作时间",hide:true},
            {field: "salary", title: "就业薪资"},
            {field: "companyName", title: "就业公司",hide:true},
            {field: "job", title: "就职岗位",hide:true},
            {field: "companyCity", title: "公司城市",hide:true},
            {field: "typeName", title: "就业类型",hide:true},
            {field: "remark", title: "备注",hide:true},
            {title: "操作", width: 150, align: "center", fixed: "right", toolbar: "#studentGraduates-list-manage-btn"}
        ]],
        toolbar: '#studentGraduates-table-manage-btn',
        defaultToolbar: ['filter', 'exports', {
            title: '提示' //标题
            , layEvent: 'LAYTABLE_TIPS' //事件名，用于 toolbar 事件中使用
            , icon: 'layui-icon-tips' //图标类名
        }],
        page: true,
        limit: 30,
        height: 'full-200',
    }), table.on("toolbar(LAY-studentGraduates-manage)", function (e) {
        active[e.event](e);
    }), table.on("tool(LAY-studentGraduates-manage)", function (e) {
        active[e.event](e);
    }), e("studentGraduates", {});

    //事件
    var active = {
        "batchdel": function (e) {
            var checkStatus = table.checkStatus('LAY-studentGraduates-manage')
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
                    url: '/studentGraduatess',
                    type: 'delete',
                    data: {ids: ids},
                    done: function (e) {
                        layer.msg(e.msg);
                            if (e.code === 0) {
                                setTimeout(function () {
                                    layui.table.reload("LAY-studentGraduates-manage"), layer.close(index)
                                }, 1000);
                            }
                    }
                });
            });
        }, "add": function (e) {
            window.studentGraduates =  null;

            location.hash = "/teaching/studentGraduates/form";
            /*admin.popup({
                title: '添加',
                area: ['870px', '550px'],
                id: 'LAY-popup-studentGraduates-add',
                success: function (layero, index) {
                    view(this.id).render('teaching/studentGraduates/form').done(function () {
                        window.initForm();
                        // form.render(null, 'layadmin-studentGraduates-formlist');

                        //监听提交
                     /!*   form.on('submit(LAY-studentGraduates-front-submit)', function (data) {
                            // 提交数据到后台
                            admin.req({
                                url: '/studentGraduatess',
                                type: 'post',
                                data: data.field,
                                done: function (e) {
                                    layer.msg(e.msg);
                                    if (e.code === 0) {
                                        setTimeout(function () {
                                            layui.table.reload("LAY-studentGraduates-manage"), layer.close(index)
                                        }, 1000);
                                    }
                                }
                            });

                            return false;
                        });*!/
                    });
                }
            });*/
        }, "edit": function (e) {
            window.studentGraduates =  e.data;

            location.hash = "/teaching/studentGraduates/form";
            /* admin.popup({
               title: "编辑",
               area: ['870px', '550px'],
               id: "LAY-popup-studentGraduates-add",
               success: function (e, i) {
                   view(this.id).render("teaching/studentGraduates/form", data).done(function () {
                         initForm(data);
                         // 数据回显
                         form.val("layadmin-studentGraduates-formlist", data);
                         // 重新渲染页面
                         form.render(null, "layadmin-studentGraduates-formlist"),
                         // 绑定提交表单事件
                         form.on("submit(LAY-studentGraduates-front-submit)", function (e) {
                             // 提交数据到后台
                             admin.req({
                                 url: '/studentGraduatess',
                                 type: 'put',
                                 data: e.field,
                                 done: function (e) {
                                     layer.msg(e.msg);
                                     if (e.code === 0) {
                                         setTimeout(function () {
                                         layui.table.reload("LAY-studentGraduates-manage"), layer.close(i)
                                     }, 1000);
                                 }
                                 }
                             });
                             return false;
                         });
                     });
                }
            });*/
        }, "del": function(e) {
            layer.confirm("确定要删除该数据吗？", function (i) {
                if (e.data.id) {
                    admin.req({
                        url: '/studentGraduatess',
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