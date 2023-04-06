CREATE TABLE grocery_lists (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE meals (
   id INTEGER AUTO_INCREMENT PRIMARY KEY,
   grocery_lists_key INTEGER,
   name VARCHAR(500) NOT NULL,
   INDEX grocery_lists_key_idx (grocery_lists_key),
   FOREIGN KEY (grocery_lists_key) REFERENCES grocery_lists(id)
);

CREATE TABLE ingredients (
   id INTEGER AUTO_INCREMENT PRIMARY KEY,
   meals_key INTEGER,
   name VARCHAR(500) NOT NULL,
   measure VARCHAR(100) NOT NULL,
   INDEX meals_key_idx (meals_key),
   FOREIGN KEY (meals_key) REFERENCES meals(id)
);
