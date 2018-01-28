var app = angular.module("cadUsuario", ['ngCookies']);

app.value('urlBase', 'http://localhost:8084/sProdutos/rest/');

app.controller("cadUsuarioCtl", function ($scope, $window, $http, urlBase, $cookies) {
    $scope.login = {id: "", nome: "", idade: "", endereco: "", telefone: "", usuario: "", senha: ""};
    $scope.usuario = {usuario: "", token: ""};
    $scope.cad = {nome: "", idade: "", endereco: "", telefone: "", usuario: "", senha: ""};
    $scope.listUses = [];
    
    //Irá chamar o metodo para closeApp assim que a pagina for fechada
    //Garantido que o usuario não fique logado
    $scope.setConfirmUnload = function(on) {
        window.onbeforeunload = (on) ? $scope.closeApp : null;
    };
    
    $scope.logarUser = function (login) {
        $http({method: 'POST',
            url: urlBase + "acessologin",
            data: login
        }).then(function (response) {

            $scope.usuario = response.data;

            if ($scope.usuario.usuario === "0") {
                console.log("Usuario não encontrado");
            } else {
                $cookies.put('cookie', $scope.usuario.token);
                $cookies.put('user', $scope.usuario.usuario);
                $window.location.href = 'exibicao.html';
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
            if (status === "true") {
                console.log("Usuario encontrado");
                $window.location.href = 'index.html';
            } else {
                console.log("Usuario não encontrado");
            }
        });

    };

    $scope.exibeUsuarios = function () {

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

        $http({method: 'DELETE',
            url: urlBase + "acessologin/exibicao/" + $cookies.get('cookie')
        }).then(function (response) {
            
            console.log(response.data);
            $cookies.remove('cookie');
            $cookies.remove('user');
            $window.location.href = 'index.html';
        });
    };
});


