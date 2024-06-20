<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background-color: #f5f5f5;
        }
        .login-container {
            background-color: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            max-width: 400px;
            width: 100%;
        }
        .login-container h2 {
            margin-bottom: 20px;
        }
        .login-container form {
            display: flex;
            flex-direction: column;
        }
        .login-container form div {
            margin-bottom: 15px;
        }
        .login-container form div label {
            margin-bottom: 5px;
        }
        .login-container form div input {
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
        .login-container form button {
            padding: 10px;
            background-color: #28a745;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .login-container form button:hover {
            background-color: #218838;
        }
        .error-message {
            color: red;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
<div class="login-container">
    <h2>Login</h2>
    <div class="error-message" id="error-message"></div>
    <form id="login-form">
        <div>
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>
        </div>
        <div>
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
        </div>
    </form>
    <button id="loadHomeBtn">Load Home</button>
</div>

<script>
    $(document).ready(function() {
        $('#loadHomeBtn').click(function() {
            const username = $('#username').val();
            const password = $('#password').val();

            $.ajax({
                type: 'GET',
                url: '/api/bs/er/token/get-bs-er-new-token',
                data: {
                    loginId: username,
                    pwd: password
                },
                success: function(data, textStatus, jqXHR) {
                    console.log('Authorization data:', data);
                    // 서버로부터 받은 데이터를 로컬 스토리지에 저장
                    if (data.accessToken && data.refreshToken) {
                        localStorage.setItem('accessToken', data.accessToken);
                        localStorage.setItem('refreshToken', data.refreshToken);

                        // /jsp/home 페이지로 이동할 때 Authorization 헤더 설정
                        const xhr = new XMLHttpRequest();
                        xhr.open('GET', 'test/jsp/home', true);
                        xhr.setRequestHeader('Authorization', 'Bearer ' + data.accessToken);
                        xhr.onload = function() {
                            if (xhr.status === 200) {
                                window.location.href = 'test/jsp/home';
                            } else {
                                console.error('Failed to load home page:', xhr.statusText);
                            }
                        };
                        xhr.send();
                    } else {
                        $('#error-message').text('Invalid login response');
                    }
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    console.error('AJAX Error:', textStatus, errorThrown);
                }
            });
        });
    });
</script>
</body>
</html>
