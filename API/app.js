// index.js
const express = require('express');
const mysql = require('mysql2');
const bodyParser = require('body-parser');
const app = express();
const port = 3000;

// Sử dụng bodyParser để đọc dữ liệu từ body của request
app.use(bodyParser.json());

// Kết nối đến MySQL
const connection = mysql.createConnection({
  host: 'localhost',
  user: 'root',
  password: '12345678',
  database: 'Book'
});

connection.connect((err) => {
  if (err) {
    console.error('Error connecting to MySQL: ' + err.stack);
    return;
  }
  console.log('Connected to MySQL as id ' + connection.threadId);
});

// Lấy tất cả sách
app.get('/books', (req, res) => {
  connection.query('SELECT * FROM book', (error, results, fields) => {
    if (error) throw error;
    res.json(results);
  });
});

app.get('/user', (req, res) => {

    connection.query('SELECT * FROM user', (error, results, fields) => {
        if (error) throw error;
    res.json(results);
    });
});

// API đăng ký
app.post('/user', (req, res) => {
    const { email, password } = req.body;

    // Kiểm tra xem email đã tồn tại trong cơ sở dữ liệu chưa
    connection.query('SELECT * FROM user WHERE email = ?', [email], (selectError, selectResults, selectFields) => {
        if (selectError) {
            // Lỗi khi thực hiện truy vấn SELECT
            res.json({ success: false, message: 'Đăng ký không thành công' });
        } else {
            // Nếu có kết quả từ truy vấn SELECT, email đã tồn tại
            if (selectResults.length > 0) {
                res.json({ success: false, message: 'Email đã tồn tại, đăng ký không thành công' });
            } else {
                // Nếu không có kết quả, thực hiện truy vấn INSERT
                connection.query('INSERT INTO user (email, password) VALUES (?, ?)', [email, password], (insertError, insertResults, insertFields) => {
                    if (insertError) {
                        // Lỗi khi thực hiện truy vấn INSERT
                        res.json({ success: false, message: 'Đăng ký không thành công' });
                    } else {
                        // Đăng ký thành công
                        res.json({ success: true, message: 'Đăng ký thành công' });
                    }
                });
            }
        }
    });
});


// Lấy thông tin sách theo ID
app.get('/books/:id', (req, res) => {
  const bookId = req.params.id;
  connection.query('SELECT * FROM book WHERE id = ?', [bookId], (error, results, fields) => {
    if (error) throw error;
    res.json(results[0]);
  });
});

// Thêm sách mới
app.post('/books', (req, res) => {
  const { avatar, name } = req.body;
  connection.query('INSERT INTO book (name,avatar) VALUES (?, ?)', [name, avatar], (error, results, fields) => {
    if (error) throw error;
    res.json({ message: 'Book added successfully', bookId: results.insertId });
  });
});

// Sửa thông tin sách
app.put('/books/:id', (req, res) => {
  const bookId = req.params.id;
  const { avatar, name } = req.body;
  connection.query('UPDATE book SET avatar = ?, name = ? WHERE id = ?', [avatar, name, bookId], (error, results, fields) => {
    if (error) throw error;
    res.json({ message: 'Book updated successfully' });
  });
});

// Xóa sách
app.delete('/books/:id', (req, res) => {
  const bookId = req.params.id;
  connection.query('DELETE FROM book WHERE id = ?', [bookId], (error, results, fields) => {
    if (error) throw error;
    res.json({ message: 'Book deleted successfully' });
  });
});

app.listen(port, () => {
  console.log(`Server is running at http://localhost:${port}`);
});
