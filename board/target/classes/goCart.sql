CREATE TABLE `board` (
   `id` bigint NOT NULL AUTO_INCREMENT primary key,
   `img` varchar(300) not null,
   `title` varchar(300) NOT NULL,
   `con` text,
   `file` varchar(300) NOT NULL,
   `multipartFile` varchar(300),
   `originalFile` varchar(300) NOT NULL
 );
 select * from board.board;
commit;