var app = angular.module("cadUsuario", []);

app.value('urlBase', 'http://localhost:8084/sProdutos/rest/');

app.controller("cadUsuarioCtl", function ($scope, $window, $http, urlBase) {
    $scope.login = {id: "", nome: "", idade: "", endereco: "", telefone: "", usuario: "", senha: ""};
    $scope.usuario = {id: "", nome: "", idade: "", endereco: "", telefone: ""};
    $scope.cad = [{nome: "", idade: "", endereco: "", telefone: ""}];

    $scope.logarUser = function (login) {
        $http({method: 'POST',
            url: urlBase + "acessologin",
            data: login
        }).then(function (response) {
            console.log(response.data.toString());
            $scope.usuario = response.data;

            if ($scope.usuario.id === -1) {
                console.log("Usuario não encontrado");
            } else {
                console.log("Usuario encontrado");
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
            status = response.data.toString();

            if (status === false) {
                console.log("Usuario encontrado");
                $window.location.href = 'index.html';
            } else {
                console.log("Usuario não encontrado");
            }
        });

    };
});


