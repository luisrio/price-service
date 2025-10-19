DROP TABLE IF EXISTS prices;
CREATE TABLE prices (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    brand_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    price_list INT NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    priority INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    currency VARCHAR(3) NOT NULL
);

CREATE INDEX idx_prices_product_brand ON prices(product_id, brand_id);
CREATE INDEX idx_prices_interval ON prices(start_date, end_date);
CREATE INDEX idx_prices_priority ON prices(priority);