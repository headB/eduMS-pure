/** layuiAdmin.pro-v1.2.1 LPPL License By http://www.layui.com/admin/ */
;layui.define(["table", "form", "layer"], function (e) {
    var $ = layui.$, admin = layui.admin, layer = layui.layer, view = layui.view, table = layui.table,
        form = layui.form,
        token = layui.data(layui.setter.tableName)[layui.setter.request.tokenName];
    table.render({
        elem: "#LAY-classinfo-manage",
        url: "/classinfos",
        headers: {'x-auth-token': token},
        request: {pageName: 'current', limitName: 'size'},
        initSort: {field:'beginTime', type:'desc'},
        autoSort:false,
        cols: [[
            {type: "radio", fixed: "left"},
            {field: "title", title: "班级标题",width:300},
            {field: "campusTitle", title: "所属校区",width:110},
            {field: "productTitle", title: "产品类型",width:130},
            {field: "beginTime", title: "开班时间",sort: true,width:110},
            {field: "endTime", title: "毕业时间",hide:true},
            {field: "classroomName", title: "教室"},
            {field: "statusName", title: "状态"},
            {field: "fee", title: "原价"},
            {field: "discountFee", title: "优惠价"},
            {field: "zeroPayFee", title: "零付款价格"},
            {field: "classTeacherName", title: "主班主任"},
            {field: "secondTeacherName", title: "副班主任",hide:true},
            {field: "planNumber", title: "计划人数"},
            {field: "teamReward", title: "团队奖励",hide:true,hide:true},
            {field: "studentNum", title: "学生总数",hide:true},
            {field: "upGradeNum", title: "升学数",hide:true},
            {field: "lowerGradeNum", title: "留级数",hide:true},
            {title: "操作", width: 150, align: "center", fixed: "right", toolbar: "#classinfo-list-manage-btn"}
        ]],
        toolbar: '#classinfo-table-manage-btn',
        defaultToolbar: ['filter', 'exports', {
            title: '提示' //标题
            , layEvent: 'LAYTABLE_TIPS' //事件名，用于 toolbar 事件中使用
            , icon: 'layui-icon-tips' //图标类名
        }],
        page: true,
        limit: 30,
        height: 'full-200',
    }), table.on("toolbar(LAY-classinfo-manage)", function (e) {
        active[e.event](e);
    }),table.on("tool(LAY-classinfo-manage)", function (e) {
        active[e.event](e);
    }), e("classinfo", {});


//事件
var active = {
    add: function (e) {
        admin.popup({
            title: '添加'
            , area: ['500px', '450px']
            , id: 'LAY-popup-classinfo-add'
            , success: function (layero, index) {
                view(this.id).render('teaching/classinfo/form').done(function () {
                    form.render(null, 'layadmin-classinfo-formlist');

                    //监听提交
                    form.on('submit(LAY-classinfo-front-submit)', function (data) {
                        // 提交数据到后台
                        admin.req({
                            url: '/classinfos',
                            type: 'post',
                            data: data.field,
                            done: function (e) {
                                layer.msg(e.msg);
                                if (e.code === 0) {
                                    setTimeout(function () {
                                        layui.table.reload("LAY-classinfo-manage"), layer.close(index)
                                    }, 1000);
                                }
                            }
                        });

                        return false;
                    });
                });
            }
        });
    },
    edit:function (e) {
        var data = e.data;
        admin.popup({
            title: "编辑",
            area: ["500px", "450px"],
            id: "LAY-popup-classinfo-add",
            success: function (e, i) {
                view(this.id).render("teaching/classinfo/form", data).done(function () {
                    // 数据回显
                    form.val("layadmin-classinfo-formlist", data);
                    // 重新渲染页面
                    form.render(null, "layadmin-classinfo-formlist");
                    // 绑定提交表单事件
                    form.on("submit(LAY-classinfo-front-submit)", function (e) {
                        // 提交数据到后台
                        admin.req({
                            url: '/classinfos',
                            type: 'put',
                            data: e.field,
                            done: function (e) {
                                layer.msg(e.msg);
                                if (e.code === 0) {
                                    setTimeout(function () {
                                        layui.table.reload("LAY-classinfo-manage"), layer.close(i)
                                    }, 1000);
                                }
                            }
                        });
                        return false;
                    })
                })
            }
        });
    },
    del:function (e) {
        layer.confirm("确定要禁用该数据吗？", function (i) {
            if (e.data.id) {
                admin.req({
                    url: '/classinfos',
                    data: {id: e.data.id},
                    type: 'delete',
                    done: function (res) {
                        layer.msg(res.msg);
                        if (res.code === 0) {
                            setTimeout(function () {
                                layui.table.reload("LAY-classinfo-manage"), layer.close(i)
                            }, 1000);
                        }
                    }
                });
            }
            //e.del(), layer.close(i)
        });
    },
    graduate:function (e) {
        var checkStatus = table.checkStatus('LAY-classinfo-manage')
            , checkData = checkStatus.data; //得到选中的数据

        if (checkData.length === 0) {
            return layer.msg('请选择数据');
        }

        var classinfo = checkData[0];

        if(classinfo.status == 2){
            return layer.msg('该班级已经是毕业状态，不能重复设置');
        }
        layer.confirm("确定要设置为毕业吗?(该操作会生成学员毕业数据)", function (i) {
            var index;
            admin.req({
                url: '/classinfos',
                data: {id: classinfo.id,status:2},
                type: 'patch',
                beforeSend:function () {
                    index = layer.msg('数据生成中', {
                        icon: 16,
                        time : false,
                        shade: 0.01
                    });
                }
                ,done: function (e) {
                    layer.msg(e.msg);
                    if (e.code === 0) {
                        layer.close(index);
                        setTimeout(function () {
                            layui.table.reload("LAY-classinfo-manage"), layer.close(i)
                        }, 1000);
                    }

                }
            });
        });
    }
};

});