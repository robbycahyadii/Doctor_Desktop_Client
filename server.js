const express = require('express');
const bodyParser = require('body-parser');
const mysql = require('mysql');

const app = express();
const PORT = 3000;

app.use(bodyParser.json());

// Koneksi ke database MySQL
const db = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '', // ganti dengan password MySQL Anda
    database: 'consultation'
});

db.connect(err => {
    if (err) {
        console.error('Error connecting to the database:', err);
        return;
    }
    console.log('Connected to the MySQL database.');
});

app.use((req, res, next) => {
    console.log("Received JSON: ", req.body);
    next();
});

// Endpoint untuk login dokter
app.post('/loginDoctor', (req, res) => {
    const { username, password } = req.body;

    const sql = 'SELECT * FROM doctors WHERE username = ? AND password = ?';
    db.query(sql, [username, password], (err, results) => {
        if (err) {
            res.status(500).json({ error: err.message });
            return;
        }
        if (results.length > 0) {
            res.status(200).json({ message: 'Login successful', doctor: results[0] });
        } else {
            res.status(400).json({ message: 'Invalid username or password' });
        }
    });
});

// Endpoint untuk login user
app.post('/loginUser', (req, res) => {
    const { username, password } = req.body;

    const sql = 'SELECT * FROM users WHERE username = ? AND password = ?';
    db.query(sql, [username, password], (err, results) => {
        if (err) {
            res.status(500).json({ error: err.message });
            return;
        }
        if (results.length > 0) {
            res.status(200).json({ message: 'Login successful', user: results[0] });
        } else {
            res.status(400).json({ message: 'Invalid username or password' });
        }
    });
});

// Endpoint untuk memilih dokter
app.post('/selectDoctor', (req, res) => {
    const { doctorId } = req.body;

    // Set status semua dokter menjadi 0
    db.query('UPDATE doctors SET status = 0', (err, results) => {
        if (err) {
            res.status(500).json({ error: err.message });
            return;
        }

        // Set status dokter yang dipilih menjadi 1
        const sql = 'UPDATE doctors SET status = 1 WHERE id = ?';
        db.query(sql, [doctorId], (err, results) => {
            if (err) {
                res.status(500).json({ error: err.message });
                return;
            }
            if (results.affectedRows > 0) {
                res.status(200).json({ message: 'Doctor selected successfully' });
            } else {
                res.status(400).json({ message: 'Doctor not found' });
            }
        });
    });
});

// Endpoint untuk mendapatkan daftar dokter
app.get('/getDoctors', (req, res) => {
    const query = 'SELECT id, name, specialization, contact_info, pengalaman FROM doctors';
    db.query(query, (err, results) => {
        if (err) {
            res.status(500).send(err);
        } else {
            res.json(results);
        }
    });
});

// Endpoint untuk mendapatkan data user berdasarkan user_id
app.get('/getUser', (req, res) => {
    const { user_id } = req.query;
    const sql = 'SELECT qty, medicine FROM users WHERE id = ?';
    db.query(sql, [user_id], (err, results) => {
        if (err) {
            res.status(500).json({ error: err.message });
            return;
        }
        if (results.length > 0) {
            res.status(200).json(results[0]);
        } else {
            res.status(400).json({ message: 'User not found' });
        }
    });
});


/*
    // Endpoint untuk mendapatkan daftar dokter
    app.get('/getDoctors', (req, res) => {
        const sql = 'SELECT * FROM doctors';
        db.query(sql, (err, results) => {
            if (err) {
                res.status(500).json({ error: err.message });
                return;
            }
            res.status(200).json(results);
        });
    });
*/

// Endpoint untuk dokter mengirim obat ke pasien dan mengubah qty di tabel users
app.post('/sendMedicine', (req, res) => {
    const { userId, medicine, quantity } = req.body;

    // Update qty pada tabel users
    const sqlUpdate = 'UPDATE users SET qty = qty + ?, medicine = ? WHERE id = ?';
    db.query(sqlUpdate, [quantity, medicine, userId], (err, result) => {
        if (err) {
            res.status(500).json({ error: err.message });
            return;
        }
        res.status(200).json({ message: 'Medicine sent successfully.' });
    });
});

// Endpoint untuk memberikan uang kepada dokter dan mengubah saldo di tabel doctors
app.post('/giveMoneyToDoctor', (req, res) => {
    const { userId, doctorId, amount } = req.body;

    // Menambah saldo dokter
    const sqlUpdateDoctor = 'UPDATE doctors SET saldo = saldo + ? WHERE id = ?';
    db.query(sqlUpdateDoctor, [amount, doctorId], (err, result) => {
        if (err) {
            res.status(500).json({ error: err.message });
            return;
        }
        res.status(200).json({ message: 'Money transferred successfully.' });
    });
});

// Endpoint untuk registrasi user
app.post('/registerUser', (req, res) => {
    const { username, password } = req.body;

    const sql = 'INSERT INTO users (username, password) VALUES (?, ?)';
    db.query(sql, [username, password], (err, results) => {
        if (err) {
            if (err.code === 'ER_DUP_ENTRY') {
                res.status(400).json({ message: 'Username already exists' });
            } else {
                res.status(500).json({ error: err.message });
            }
            return;
        }
        res.status(201).json({ message: 'User registered successfully' });
    });
});
/*
// Endpoint to get chat messages between user and doctor
app.get('/getChats', (req, res) => {
    const { userId, doctorId } = req.query;

    const sql = 'SELECT * FROM chats WHERE user_id = ? AND doctor_id = ?';
    db.query(sql, [userId, doctorId], (err, results) => {
        if (err) {
            res.status(500).json({ error: err.message });
            return;
        }
        res.status(200).json(results);
    });
});
*/
app.get('/getChats', (req, res) => {
    const { user_id, doctor_id } = req.query;
    // Assuming you're querying chats from a database
    db.query('SELECT * FROM chats WHERE user_id = ? AND doctor_id = ?', [user_id, doctor_id], (err, rows) => {
        if (err) {
            console.error(err);
            return res.status(500).json({ error: 'Internal Server Error' });
        }
        // Assuming rows is an array of chat messages
        res.json(rows);
    });
});

// Endpoint to send a chat message
app.post('/sendMessage', (req, res) => {
    const { user_id, doctor_id, message, sender } = req.body;

    const sql = 'INSERT INTO chats (user_id, doctor_id, message, sender) VALUES (?, ?, ?, ?)';
    db.query(sql, [user_id, doctor_id, message, sender], (err, results) => {
        if (err) {
            res.status(500).json({ error: err.message });
            return;
        }
        res.status(201).json({ message: 'Chat sent successfully' });
    });
});

// Get Messages
app.get('/getMessages/:doctor_id', (req, res) => {
    const doctorId = req.params.doctor_id;
    const sql = 'SELECT * FROM chats WHERE doctor_id = ? ORDER BY id ASC';
    db.query(sql, [doctorId], (err, result) => {
        if (err) {
            res.status(500).json({ error: err.message });
            return;
        }
        res.status(200).json(result);
    });
});

// Endpoint untuk mendapatkan profil dokter berdasarkan doctorId
app.get('/getDoctorProfile/:doctorId', (req, res) => {
    const doctorId = req.params.doctorId;
    const sql = 'SELECT id, name, specialization, contact_info, saldo, pengalaman, username, status FROM doctors WHERE id = ?';
    db.query(sql, [doctorId], (err, results) => {
        if (err) {
            res.status(500).json({ error: err.message });
            return;
        }
        if (results.length > 0) {
            res.status(200).json(results[0]);
        } else {
            res.status(404).json({ message: 'Doctor not found' });
        }
    });
});

// Start server
app.listen(PORT, () => {
    console.log(`Server running on port ${PORT}`);
});
