<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>RSS App</title>
    <link rel="stylesheet" href="https://unpkg.com/purecss@1.0.1/build/pure-min.css"
          integrity="sha384-oAOxQR6DkCoMliIh8yFnu25d7Eq/PHS21PClpwjOTeU2jRSq11vu66rf90/cZr47" crossorigin="anonymous">

</head>
<body>
<main style=" width: 60%; margin: 0 auto; text-align: center">
    <h1>RSS App</h1>
    <form id="mainForm" class="pure-form">
        <div>
            <input type="email" id="emailAddress" class="pure-input-rounded pure-input-2-3" placeholder="e-mail address" style="width: 69%">
            <button id="addUser" class="pure-button pure-button-primary" style="width: 20%">Confirm</button>
        </div>
        <div style="margin-top: 3px">
            <input type="url" id="rssHyperlink" class="pure-input-rounded pure-input-2-3" placeholder="URL" style="width: 90%">
        </div>
    </form>
    <div style="text-align: left">
        <fieldset style="width: 45%; height: 40%; float: left" >
            <legend><b>Content:</b></legend>
            <div id="emailView" style="height: 90%; overflow: auto"></div>
            <button id="addRss" class="pure-button pure-button-primary" style="width: 100%; margin-top: 30px">Save RSS</button>
        </fieldset>
        <fieldset style="width: 45%; height: 40%; float: left">
            <legend><b>RSS List:</b></legend>
            <div id="allRss" style="height: 90%; overflow: auto"></div>
            <button id="sendEmail" class="pure-button pure-button-primary" style="width: 100%; margin-top: 30px">Send E-mail</button>
        </fieldset>
    </div>

</main>
<script>
    (function() {
        // const USER_URL = 'http://localhost:8080/user';
        // const RSS_URL = 'http://localhost:8080/rss';
        // const SEND_URL = 'http://localhost:8080/send';
        const USER_URL = 'https://cekus-rss-app.azurewebsites.net/user';
        const RSS_URL = 'https://cekus-rss-app.azurewebsites.net/rss';
        const SEND_URL = 'https://cekus-rss-app.azurewebsites.net/send';

        const emailAddress = document.getElementById('emailAddress');
        const rssHyperlink = document.getElementById('rssHyperlink');
        // add new email (User)
        document.getElementById('addUser').addEventListener('click', (event) => {
            event.preventDefault();
            fetch(USER_URL, {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ email: emailAddress.value })
            })
                .then(processOkResponse)
                .then(() => emailAddress.readOnly = true)
                .then(() => document.getElementById('addUser').readOnly = true)
                .catch(console.warn);

        });

        // add new RSS
        document.getElementById('addRss').addEventListener('click', (event) => {
            event.preventDefault();
            fetch(RSS_URL + "?" + new URLSearchParams({email: emailAddress.value}), {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ hyperlink: rssHyperlink.value })
            })
                .then(processOkResponse)
                .then(createNewRss)
                .then(() => rssHyperlink.value = '')
                .catch(console.warn);

        });

        // send RSS content to e-mail address
        document.getElementById('sendEmail').addEventListener('click', (event) => {
            event.preventDefault();
            fetch(SEND_URL + "?" + new URLSearchParams({email: emailAddress.value}));
            fetch(RSS_URL)
                .then(response => response.text())
                .then((text) => {
                    document.getElementById('emailView').innerHTML = text
                })
        });

        // add RSS to RSS List
        function createNewRss(rss) {
            const paragraph = document.createElement('p');
            paragraph.appendChild(document.createTextNode(rss.hyperlink));
            document.getElementById('allRss').appendChild(paragraph);
        }

        function processOkResponse(response = {}) {
            if (response.ok) {
                return response.json();
            }
            throw new Error(`Status not 200 (` + response.status + `)`);
        }
    })();
</script>
</body>
</html>
