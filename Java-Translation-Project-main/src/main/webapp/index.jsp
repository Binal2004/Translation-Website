<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Google API Translator App</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            background: linear-gradient(-45deg, #ee7752, #e73c7e, #23a6d5, #23d5ab);
            background-size: 400% 400%;
            animation: gradientBG 15s ease infinite;
        }

        @keyframes gradientBG {
            0% {
                background-position: 0% 50%;
            }
            50% {
                background-position: 100% 50%;
            }
            100% {
                background-position: 0% 50%;
            }
        }

        .container {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0px 0px 30px rgba(0, 0, 0, 0.1);
            width: 400px;
            max-width: 90%;
            animation: fadeIn 1.5s ease-in-out;
        }

        @keyframes fadeIn {
            from {
                opacity: 0;
                transform: scale(0.9);
            }
            to {
                opacity: 1;
                transform: scale(1);
            }
        }

        h1 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
            font-size: 1.8em;
        }

        textarea {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border-radius: 5px;
            border: 1px solid #ddd;
            font-size: 1em;
        }

        label {
            font-weight: bold;
            margin-bottom: 10px;
            display: block;
            color: #555;
        }

        select {
            width: 100%;
            padding: 10px;
            border-radius: 5px;
            border: 1px solid #ddd;
            margin-bottom: 20px;
            font-size: 1em;
        }

        button {
            width: 100%;
            padding: 10px;
            border: none;
            border-radius: 5px;
            background-color: #007bff;
            color: white;
            font-size: 1.1em;
            cursor: pointer;
            margin-bottom: 10px;
            transition: background-color 0.3s ease;
        }

        button:hover {
            background-color: #0056b3;
        }

        .copy-button {
            margin-top: 10px;
            background-color: #28a745;
        }

        .copy-button:hover {
            background-color: #218838;
        }

    </style>
</head>
<body>
    <div class="container">
        <h1>Google API Translator App</h1>
        <form action="TranslationCode" method="post">
            <textarea name="inputText" rows="5" cols="40" placeholder="Enter text to translate"></textarea><br>

            <label for="targetLanguage">Select target language:</label>
            <select name="targetLanguage">
                <option value="fr">French</option>
                <option value="es">Spanish</option>
                <option value="de">German</option>
                <option value="hi">Hindi</option>
                <option value="it">Italian</option>
                <option value="ja">Japanese</option>
                <option value="zh">Chinese</option>
                <option value="ru">Russian</option>
                <option value="ar">Arabic</option>
                <option value="pt">Portuguese</option>
            </select><br>

            <button type="submit" name="action" value="translate">Translate</button>
            <button type="submit" name="action" value="textToSpeech">Speech</button>
            <button type="submit" name="action" value="textToSpeechTranslation">Translated Speech</button>
        </form>
    </div>

    <script>
        document.querySelector('.copy-button').addEventListener('click', function() {
            var text = document.querySelector('textarea').value;
            navigator.clipboard.writeText(text).then(function() {
                alert('Translated text copied to clipboard');
            });
        });
    </script>
</body>
</html>
