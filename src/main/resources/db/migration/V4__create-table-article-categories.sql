CREATE TABLE IF NOT EXISTS article(
    id SERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    author INT,
    content VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS article_categories(
    id_article INT NOT NULL,
    id_category INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_article, id_category),
    FOREIGN KEY (id_article) REFERENCES article(id) ON DELETE CASCADE,
    FOREIGN KEY (id_category) REFERENCES category(id) ON DELETE CASCADE
);