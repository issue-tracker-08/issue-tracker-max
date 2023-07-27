# INSERT INTO user_account(login_id, password, profile_url)
# VALUES ('bean1234', 'bean1234', 'test-url'),
#        ('tommy1234', 'tommy1234', 'test-url'),
#        ('pie1234', 'pie1234', 'test-url');
#
# INSERT INTO milestone(name, description, due_date)
# VALUES ('milestone1', 'test milestone1', CURRENT_TIMESTAMP),
#        ('milestone2', 'test milestone2', CURRENT_TIMESTAMP),
#        ('milestone3', 'test milestone3', CURRENT_TIMESTAMP);
#
# INSERT INTO label(name, description, font_color, background_color)
# VALUES ('label1', 'test label1', 'black', 'white'),
#        ('label2', 'test label2', 'green', 'red'),
#        ('label3', 'test label3', 'yellow', 'brown');
#
# INSERT INTO issue_label(issue_id, label_id) VALUES (1, 1), (1, 2), (1, 3);
#
# INSERT INTO issue(title, is_open, created_at, content, user_account_id, milestone_id)
# VALUES ('issue1', true, CURRENT_TIMESTAMP, 'content1', 1, 1),
#        ('issue2', true, CURRENT_TIMESTAMP, 'content2', 1, 2),
#        ('issue3', true, CURRENT_TIMESTAMP, 'content3', 1, 3);
#
# INSERT INTO issue_assignee(issue_id, user_account_id)
# VALUES (1, 1), (1, 2), (1, 3),
#        (2, 2), (2, 3),
#        (3, 1), (3, 3);