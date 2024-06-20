<!DOCTYPE html>
<html>
<head>
    <title>Home</title>
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
        .content-container {
            background-color: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            max-width: 600px;
            width: 100%;
        }
    </style>
</head>
<body>
<div class="content-container">
    <h2>Welcome to Home</h2>
    <div id="content"></div>
</div>

<script>
    $(document).ready(function() {
        const accessToken = localStorage.getItem('accessToken');
        console.log('@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@',accessToken);

        if (!accessToken) {
            console.log('여긴가1?ㅋ')
        } else {
            $.ajax({
                type: 'GET',
                url: '/api/bs/co/test',
                headers: {
                    'Authorization': 'Bearer ' + accessToken
                },
                success: function(data, textStatus, jqXHR) {
                    $('#content').text('Protected data: ' + JSON.stringify(data));
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    console.error('AJAX Error:', textStatus, errorThrown);
                    if (jqXHR.status === 401) {
                        console.log('여긴가2?ㅋ')
                    }
                }
            });
        }
    });
</script>
</body>
</html>
