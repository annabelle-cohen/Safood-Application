package com.example.safood.Common;

import com.example.safood.Model.Article;
import com.example.safood.Model.Notification;
import com.example.safood.Model.User;

public class Common {
    public static User currentUser = new User();
    public static Article currentArticle = new Article();
    public static Notification currentNotification = new Notification();
    public static Boolean isHistory = false;
}
