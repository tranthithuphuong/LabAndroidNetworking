create database Book;
use Book;

CREATE TABLE `user` (
  `id` INTEGER NOT NULL AUTO_INCREMENT primary key,
  `email` varchar(255),
  `password` varchar(255),
  `role` varchar(11)
);
INSERT INTO `user` (`email`, `password`, `role`) VALUES
('phuong@gmail.com', '12345', 'user'),
('cuong@gmail.com', '12345', 'admin');

CREATE TABLE `book` (
  `id` INTEGER NOT NULL AUTO_INCREMENT primary key,
  `avatar` varchar(255),
  `name` varchar(255)
);

INSERT INTO `book` (`avatar`, `name`) VALUES
('https://nld.mediacdn.vn/2020/12/11/ketnoi-1-1607689140383915149652.jpg', 'sach1'),
('https://nld.mediacdn.vn/thumb_w/684/2020/12/11/cunghoc-1-16076891569151604681097.jpg', 'sach2'),
('https://nld.mediacdn.vn/2020/12/11/chan-troi-1-16076891785101377735889.jpg', 'sach3'),
('https://nld.mediacdn.vn/2020/12/11/ketnoi-1-1607689140383915149652.jpg', 'sach4'),
('https://nld.mediacdn.vn/thumb_w/684/2020/12/11/cunghoc-1-16076891569151604681097.jpg', 'sach5'),
('https://nld.mediacdn.vn/2020/12/11/ketnoi-1-1607689140383915149652.jpg', 'sach6'),
('https://nld.mediacdn.vn/thumb_w/684/2020/12/11/cunghoc-1-16076891569151604681097.jpg', 'sach7')

