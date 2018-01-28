var app = angular.module("cadUsuario", ['ngCookies']);

app.value('urlBase', 'http://localhost:8084/sProdutos/rest/');

app.controller("cadUsuarioCtl", function ($scope, $window, $http, urlBase, $cookies) {
    $scope.login = {id: "", nome: "", idade: "", endereco: "", telefone: "", usuario: "", senha: ""};
    $scope.usuario = {usuario: "", token: ""};
    $scope.cad = {nome: "", idade: "", endereco: "", telefone: "", usuario: "", senha: ""};
    $scope.listUses = [];

    $scope.logarUser = function (login) {
        $http({method: 'POST',
            url: urlBase + "acessologin",
            data: login
        }).then(function (response) {
            
            $scope.usuario = response.data;
            
            if ($scope.usuario.usuario === "0") {
                console.log("Usuario não encontrado");
            } else {
                console.log("Usuario encontrado");
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
    
    $scope.exibeUsuarios = function(){
        
        $http({method: 'GET',
            url: urlBase + "acessologin/exibicao/"+$cookies.get('cookie')
        }).then(function (response) {
            
            console.log(response.data.length);
            if ((response.data.length === 0)) {
                console.log("Token invalido");
                $window.location.href = 'index.html';
            } else {
                console.log(response.data);
                $scope.listUses = response.data;
                $scope.usuario.nome = $cookies.get('user');
                console.log($cookies.get('cookie'));
            }
            
            /*if (status === "true") {
                console.log("Usuario encontrado");
                $window.location.href = 'index.html';
            } else {
                console.log("Usuario não encontrado");
            }*/
        });
        
    };
});


