function post(){
    var questionid= $("#question_id").val();
    var content = $("#comment_content").val();
    $.ajax({
        type: 'POST',
        url: '/comment',
        contentType:'application/json',
        data: JSON.stringify({
            'parentId':questionid,
            'content':content,
            'type':1
        }),
        success: function (response) {
            if(response.type==1){
                $("#comment_content").hide();
            }else{
                if(response.code=2001){
                    var isAccepted=confirm(response.message);
                    if(isAccepted){
                        window.open("https://github.com/login/oauth/authorize?client_id=ed675f3b9d73df6361bd&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                        window.localStorage.setItem("closable","true");
                    }
                }else{
                    alert("您的评论貌似开小差了，请稍后再试...");
                }
            }
            console.log(response)
        },
        dataType: 'json'
    });
}