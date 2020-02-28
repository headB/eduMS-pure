/** layuiAdmin.pro-v1.2.1 LPPL License By http://www.layui.com/admin/ */
;layui.define(["table", "form", "layer"], function (e) {
    var table = layui.table,
        token = layui.data(layui.setter.tableName)[layui.setter.request.tokenName];
    table.render({
        elem: "#LAY-studentTransferCharts-manage",
        url: "/charts/student/transfer",
        headers: {'x-auth-token': token},
        request: {pageName: 'current', limitName: 'size'},
        cols: [[
            {field: "campusName", title: "校区", width: 120},
            {field: "totalStuNum", title: "学生总数", width: 100},
            {field: "schoolStuNum", title: "高校学生数", width: 100},
            {field: "newStuNum", title: "新生人数", width: 100},
            {field: "midJoinNum", title: "插班人数", width: 100},
            {field: "lowerGradeNum", title: "留级人数", width: 100},
            {field: "zeroFeeNum", title: "0 学费人数", width: 100},
            {field: "onceFeeNum", title: "一次性付清人数", width: 140},
            {field: "loanNum", title: "贷款人数", width: 100},
            {field: "firedNum", title: "开除人数", width: 100},
            {field: "suspendNum", title: "休学人数", width: 100},
            {field: "comebackNum", title: "休学回归人数", width: 125},
            {field: "quitNum", title: "退学人数", width: 100},
            {field: "quitNotRefundNum", title: "退学不退费人数", width: 125},
            {field: "quitRefundNum", title: "退学退费人数", width: 125},
            {field: "lostSignUpNum", title: "报名流失人数", width: 125},
            {field: "lostNum", title: "流失人数", width: 100},
            {field: "quitRate", title: "退学率", width: 100, templet: function (d) {return d.quitRate + '%'}},
            {field: "lostRate", title: "流失率", width: 100, templet: function (d) {return d.lostRate + '%'}}
        ]],
        toolbar: true,
        defaultToolbar: ['filter', 'exports', {
            title: '提示' //标题
            , layEvent: 'LAYTABLE_TIPS' // 事件名，用于 toolbar 事件中使用
            , icon: 'layui-icon-tips' // 图标类名
        }],
        height: 'full-200'
    }), e("studentTransferCharts", {});
});