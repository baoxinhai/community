/*
提交回复
 */
function post() {
    var questionid = $("#question_id").val();
    var content = $("#comment_content").val();
    if (!content) {
        alert("回复不能为空！");
        return;
    }
    $.ajax({
        type: 'POST',
        url: '/comment',
        contentType: 'application/json',
        data: JSON.stringify({
            'parentId': questionid,
            'content': content,
            'type': 1
        }),
        success: function (response) {
            if (response.type == 1) {
                $("#comment_content").hide();
                window.location.reload();
            } else {
                if (response.code = 2001) {
                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=ed675f3b9d73df6361bd&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", "true");
                    }
                } else {
                    alert("您的评论貌似开小差了，请稍后再试...");
                }
            }
            console.log(response)
        },
        dataType: 'json'
    });
}

/**
 * 提交二级评论
 */
function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    if (!content) {
        alert("回复不能为空！");
        return;
    }
    $.ajax({
        type: 'POST',
        url: '/comment',
        contentType: 'application/json',
        data: JSON.stringify({
            'parentId': commentId,
            'content': content,
            'type': 2
        }),
        success: function (response) {
            if (response.type == 2) {
                $("#comment_content").hide();
                window.location.reload();
            } else {
                if (response.code = 2001) {
                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=ed675f3b9d73df6361bd&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", "true");
                    }
                } else {
                    alert("您的评论貌似开小差了，请稍后再试...");
                }
            }
            console.log(response)
        },
        dataType: 'json'
    });


}

/*
点击查看二级评论
 */
function collapseComment(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);

    //获取二级评论的状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {
        var subCommentContainer = $("#comment-" + id);

        if (subCommentContainer.children().length != 1) {
            comments.addClass("in");
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        } else {
            $.getJSON("/comment/" + id, function (data) {
                console.log(data);
                $.each(data.data.reverse(), function (index, comment) {
                    var avatarElement=$("<img/>",{
                        "class":"media-object img-rounded",
                        "src":comment.user.avatarUrl
                    });

                    var mediaLeftElement=$("<div/>",{
                        "class":"media-left"
                    }).append(avatarElement);

                    var mediaBodyElement=$("<div/>",{
                        "class":"media-body"
                    }).append($("<h5/>",{
                        "class":"media-heading",
                        "html":comment.user.name
                    })).append($("<div/>",{
                        "html":comment.content
                    })).append($("<div/>",{
                        "class":"menu"
                    })).append($("<span/>",{
                        "class":"pull-right",
                        "color":"#eee",
                        "html":moment(comment.gmtCreate).format('YYYY-MM-DD')
                    }));

                    var mediaElement=$("<div/>",{
                        "class":"media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments",
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });

                comments.addClass("in");
                e.setAttribute("data-collapse", "in");
                e.classList.add("active");

            });

        }


    }
}