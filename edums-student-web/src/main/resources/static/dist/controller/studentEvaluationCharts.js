/** layuiAdmin.pro-v1.2.1 LPPL License By http://www.layui.com/admin/ */
;layui.define(["table", "form", "layer"], function (e) {
    var $ = layui.$, admin = layui.admin, layer = layui.layer, view = layui.view, table = layui.table,
        form = layui.form,
        token = layui.data(layui.setter.tableName)[layui.setter.request.tokenName];
    table.render({
        elem: "#LAY-studentEvaluationCharts-manage",
        url: "/charts/student/evaluation",
        headers: {'x-auth-token': token},
        request: {pageName: 'current', limitName: 'size'},
        cols: [[
            {field: "campusName", title: "校区"},
            {field: "intentionNum", title: "意向数"},
            {field: "signUpNum", title: "报名数"},
            {field: "startStudyNum", title: "进班数"},
            {field: "recommendNum", title: "内推数"},
            {field: "recommendSignUpNum", title: "内推报名数"},
            {field: "recommendStudyNum", title: "内推进班数"},
            {field: "undergraduateNum", title: "毕业数"},
            {
                field: "recommendSignUpRate", title: "内推报名率", templet: function (d) {
                    console.log(d);
                    return d.recommendSignUpRate + '%'
                }
            },
            {
                field: "recommendStartStudyRate", title: "内推进班率", templet: function (d) {
                    return d.recommendStartStudyRate + '%'
                }
            }
        ]],
        toolbar: true,
        defaultToolbar: ['filter', 'exports', {
            title: '提示' //标题
            , layEvent: 'LAYTABLE_TIPS' //事件名，用于 toolbar 事件中使用
            , icon: 'layui-icon-tips' //图标类名
        }],
        height: 'full-200',
    }), e("studentEvaluationCharts", {});

});