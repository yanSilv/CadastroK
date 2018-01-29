var app = angular.module("cadUsuario", ['ngCookies']);

app.value('urlBase', 'http://localhost:8080/sProdutos/rest/');

app.controller("cadUsuarioCtl", function ($scope, $window, $http, urlBase, $cookies) {
    $scope.login = {id: "", nome: "", idade: "", endereco: "", telefone: "", usuario: "", senha: ""};
    $scope.conSenha = "";
    $scope.checkout = false;
    $scope.usuario = {usuario: "", token: ""};
    $scope.cad = {nome: "", idade: "", endereco: "", telefone: "", usuario: "", senha: ""};
    $scope.listUses = [];

    //Irá chamar o metodo para closeApp assim que a pagina for fechada
    //Garantido que o usuario não fique logado
    $scope.setConfirmUnload = function (on) {
        window.onbeforeunload = (on) ? $scope.closeApp : null;
    };

    $scope.logarUser = function (login) {
        $http({method: 'POST',
            url: urlBase + "acessologin",
            data: login
        }).then(function (response) {

            $scope.usuario = response.data;

            if ($scope.usuario.usuario === "0") {
                $window.window.alert("Usuario ou senha inválidos");
                $scope.login.usuario = "";
                $scope.login.senha = "";
            } else {
                $cookies.put('cookie', $scope.usuario.token);
                $cookies.put('user', $scope.usuario.usuario);
                $window.location.href = 'exibicao.html';
            }
        });
    };
    
    $scope.logarMuda = function (login) {
        $http({method: 'POST',
            url: urlBase + "acessologin",
            data: login
        }).then(function (response) {

            $scope.usuario = response.data;
            console.log($scope.usuario.usuario);
            if ($scope.usuario.usuario === "0") {
                $window.window.alert("Usuario ou senha inválidos");
                $scope.login.usuario = "";
                $scope.login.senha = "";
            } else {
                $cookies.put('cookie', $scope.usuario.token);
                $cookies.put('user', $scope.usuario.usuario);
                $scope.usuario.nome = $cookies.get('user');
                //$window.location.href = 'exibicao.html';
            }
        });
    };

    $scope.enviadados = function (cad) {
        var status;

        $http({method: 'POST',
            url: urlBase + "acessologin/cadastro",
            data: cad
        }).then(function (response) {
            status = response.data;

            console.log(status);
            if (status === "A") {
                $window.window.alert("Usuario cadastrado com sucesso");
                $window.location.href = 'index.html';
            } else if (status === "V") {
                $window.window.alert("Favor preencher todos os campos do formulário antes de enviar");
            } else if (status === "C") {
                $window.window.alert("Usuario já cadastrado, favor informar outro usuario");
                $scope.cad.usuario = "";
                $scope.cad.senha = "";
                $scope.conSenha = "";
            }
        });

    };

    $scope.exibeUsuarios = function () {
        console.log($cookies.get('cookie'));
        if (angular.isUndefined($cookies.get('cookie'))) {
            $window.location.href = 'index.html';
            return;
        }

        $http({method: 'GET',
            url: urlBase + "acessologin/exibicao/" + $cookies.get('cookie')
        }).then(function (response) {

            if ((response.data.length === 0)) {
                $window.location.href = 'index.html';
            } else {
                $scope.listUses = response.data;
                $scope.usuario.nome = $cookies.get('user');
            }
        });
    };

    $scope.closeApp = function () {
        console.log("Linha 87 ---");
        $http({method: 'DELETE',
            url: urlBase + "acessologin/delete/" + $cookies.get('cookie')
        }).then(function (response) {

            console.log(response.data);
            $cookies.remove('cookie');
            $cookies.remove('user');
            $window.location.href = 'index.html';
        });
    };

    $scope.confirmaSenha = function (senha, conSenha) {

        if (conSenha === "") {
            return;
        }

        if (senha === conSenha) {
            $scope.checkout = false;
        } else {
            $scope.checkout = true;
        }
    };
});


