CREATE TABLE rss(
    id INT NOT NULL AUTO_INCREMENT,
    hyperlink VARCHAR(255) NOT NULL,
    email INT(11) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (email) REFERENCES user(id)
);