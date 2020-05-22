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
                alert("您的评论貌似开小差了，请稍后再试...");
            }
            console.log(response)
        },
        dataType: 'json'
    });
}