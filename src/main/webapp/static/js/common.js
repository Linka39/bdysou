function reloadPage(){
    window.location.reload();
}

function submitData(){
    var q=$("#q").val();
    if(q.trim()==""){
        alert("请输入您要搜索的关键字！");
        return;
    }
    window.location.href="/search?q="+q;
}
