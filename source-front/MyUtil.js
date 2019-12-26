/*
* create at 2019-12-26
* 本JS整理一些在工作中经常使用的JS工具方法
* @Auth xiaoshaDestiny
* */


// 1、
// 获取两个日期之前的日期数组    参数是都是10为的字符串   yyyy-MM-dd
function getBetweenDateStr(start,end){
    var result = [];
    var beginDay = start.split("-");
    var endDay = end.split("-");
    var diffDay = new Date();
    var dateList = new Array;
    var i = 0;
    diffDay.setDate(beginDay[2]);
    diffDay.setMonth(beginDay[1]-1);
    diffDay.setFullYear(beginDay[0]);
    result.push(start);
    while(i == 0){
        var countDay = diffDay.getTime() + 24 * 60 * 60 * 1000;
        diffDay.setTime(countDay);
        dateList[2] = diffDay.getDate();
        dateList[1] = diffDay.getMonth() + 1;
        dateList[0] = diffDay.getFullYear();
        if( String(dateList[1]).length == 1){dateList[1] = "0"+dateList[1]};
        if( String(dateList[2]).length == 1){dateList[2] = "0"+dateList[2]};
        result.push(dateList[0]+"-"+dateList[1]+"-"+dateList[2]);
        if(dateList[0] == endDay[0] && dateList[1] == endDay[1] && dateList[2] == endDay[2]){ i = 1;
        }
    }
    return result;
}


//2、
// 工具方法 转换日期   date类型 转为 8位字符串
function parseDate(date) {
    var str = "";
    str += date.getFullYear();
    var mm = date.getMonth() + 1
    if(date.getMonth() > 9){
        str += mm;
    }
    else{
        str += "0" + mm;
    }
    if(date.getDate() > 9){
        str += date.getDate();
    }
    else{
        str += "0" + date.getDate();
    }
    return str;
}



//3、
// 工具方法 转换日期   date类型 转为 xxxx年xx月xx日
function parseDateWithNYR(date) {
    var str = "" + date.getFullYear();
    str += "年";
    var mm = date.getMonth()+1;
    if(date.getMonth() > 9){
        str += mm;
    }
    else{
        str += "0" + mm;
    }
    str += "月";
    if(date.getDate() > 9){
        str += date.getDate();
    }
    else{
        str += "0" + date.getDate();
    }
    str += "日";
    return str;
}



//4、
// 工具方法 将yyyyMMdd 转化为yyyy-MM-dd
function date8To10(value) {
    return value.substring(0, 4) + "-" + value.substring(4, 6) + "-" + value.substring(6, 8);
}


//5、
// 工具方法  将yyyy-MM-dd转化为yyyyMMdd
function date10To8(value) {
    return value.substring(0, 4) + value.substring(5, 7) + value.substring(8, 10);
}


//6、
// 正数  小数  检查规则   只允许输入正数，可以是小数
function positiveNumberCheck(value){
    value = value.replace(/[^\d,.]/g,""); //清除"数字"和"."以外的字符
    value = value.replace(/[^\d.]/g,""); //清除"数字"和"."以外的字符
    value = value.replace(/^\./g,""); //验证第一个字符是数字而不是"."
    value = value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的"."
    value = value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");//只允许输入一个小数点
    value = value.replace(/^[0]+[0-9]*$/gi,"0");//不能是0开头,输入了0 下一个不能仔输入数字，只能输入"."
    return value;
}


//7、
// layui 单击行选中radio
$('body').on('click', '.layui-table-body tr', function (e) {
    $('.layui-form-radio', this).click();
});
$('body').on('click', '.layui-form-radio', function (e) {
    e.stopPropagation();
});
table.on('row(pass-way)', function(obj){   //这里row()里面是layui 表格table的filter名称  需要修改
    //加上这两行，选择行也能选中redio
    obj.tr.find("input[lay-type='layTableRadio']").prop("checked",true);
    obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');
});




//8、
// layui 单击行勾选checkbox事件 这一串代码不用改动就能实现功能
$(document).on("click",".layui-table-body table.layui-table tbody tr", function () {
    var index = $(this).attr('data-index');
    var tableBox = $(this).parents('.layui-table-box');
    //存在固定列
    if (tableBox.find(".layui-table-fixed.layui-table-fixed-l").length>0) {
        tableDiv = tableBox.find(".layui-table-fixed.layui-table-fixed-l");
    } else {
        tableDiv = tableBox.find(".layui-table-body.layui-table-main");
    }
    var checkCell = tableDiv.find("tr[data-index=" + index + "]").find("td div.laytable-cell-checkbox div.layui-form-checkbox I");
    if (checkCell.length>0) {
        checkCell.click();
    }
});
$(document).on("click", "td div.laytable-cell-checkbox div.layui-form-checkbox", function (e) {
    e.stopPropagation();
});





