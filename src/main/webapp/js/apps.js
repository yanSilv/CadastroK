var app = angular.module("cadUsuario", ['ngCookies']);

app.value('urlBase', 'http://localhost:8000/sProdutos-1.3/rest/');

app.controller("cadUsuarioCtl", function ($scope, $window, $http, urlBase, $cookies) {
    $scope.login = {id: "", nome: "", idade: "", endereco: "", telefone: "", usuario: "", senha: "", status: ""};
    $scope.conSenha = "";
    $scope.checkout = false;
    $scope.usuario = {usuario: "", token: "", status: ""};
    $scope.cad = {nome: "", idade: "", endereco: "", telefone: "", usuario: "", senha: "", status: "Visitante"};
    $scope.listUses = [];
    $scope.listNoti = [];
    $scope.ComoboBox = [
        {id: '1', name: "Admiinitrador"}, 
        {id: '2', name: "Visitante"},
        {id: '3', name: "Funcionario"}
    ];
    $scope.listStatus = [{ADM: "Admiinitrador", VIS: "Visitante", FUN: "Funcionario"}];
    //Bloqueia os campos que o funcionario não pode alterar
    $scope.formFunc = false;    
    
    //Exibe o campo função, para alteração do cargo
    $scope.formStatus = false;  
    
    //Exibe o formulario de cadastro.
    $scope.formCadastro = false; 
    
    //Exibe os funcionarios cadastrados
    $scope.formExibicao = false; 

    //Irá chamar o metodo para closeApp assim que a pagina for fechada
    //Garantido que o usuario não fique logado
    $scope.setConfirmUnload = function (on) {
        //window.onbeforeunload = (on) ? $scope.closeApp : null;
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
                $cookies.put('status', $scope.usuario.status);
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
                $cookies.put('status', $scope.usuario.status);
                $scope.formExibicao = true;
                for (var i = 0; i < $scope.listStatus.length; i++) {
                    if ( $cookies.get('status') === $scope.listStatus[i].ADM) {
                        $scope.usuario.nome = $scope.listStatus[i].ADM +":"+ $cookies.get('user');
                        break;
                    }
                    if ( $cookies.get('status') === $scope.listStatus[i].FUN) {
                        $scope.usuario.nome = $scope.listStatus[i].FUN +":"+ $cookies.get('user');
                        break;
                    }
                    if ( $cookies.get('status') === $scope.listStatus[i].VIS) {
                        $scope.usuario.nome = $scope.listStatus[i].VIS +":"+ $cookies.get('user');
                        break;
                    }
                    if ( $cookies.get('status') === "null") {
                        $scope.usuario.nome = $scope.listStatus[i].VIS +":"+ $cookies.get('user');
                        break;
                    }
                }
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
    
    $scope.editaUsuario = function (cad, usuario) {
        var status;
        
        usuario.status = $cookies.get('status');
        usuario.token  = $cookies.get('cookie');
        
        $http({method: 'POST',
            url: urlBase + "acessologin/edidatUsario",
            data: {cad, usuario}
        }).then(function (response) {
            status = response.data;

            console.log(status);
            if (status === "A") {
                $window.window.alert("Usuario alterado com sucesso");
                $scope.exibeUsuarios();
            } else if (status === "V") {
                $window.window.alert("Favor preencher todos os campos do formulário antes de enviar");
            }
        });

    };

    $scope.exibeUsuarios = function () {
        console.log($cookies.get('cookie'));
        if (angular.isUndefined($cookies.get('cookie'))) {
            $window.location.href = 'index.html';
            return;
        }
        
        $scope.exibeNoticia();
        
        $http({method: 'GET',
            url: urlBase + "acessologin/exibicao/" + $cookies.get('cookie')
        }).then(function (response) {

            if ((response.data.length === 0)) {
                $window.location.href = 'index.html';
            } else {
                $scope.listUses = response.data;
                $scope.formFunc = false; 
                $scope.formStatus = false;
                $scope.formCadastro = false;
                $scope.formExibicao = true;
                for (var i = 0; i < $scope.listStatus.length; i++) {
                    if ( $cookies.get('status') === $scope.listStatus[i].ADM) { 
                        $scope.usuario.nome = $scope.listStatus[i].ADM +":"+ $cookies.get('user');
                        break;
                    }
                    if ( $cookies.get('status') === $scope.listStatus[i].FUN) {
                        $scope.usuario.nome = $scope.listStatus[i].FUN +":"+ $cookies.get('user');
                        break;
                    }
                    if ( $cookies.get('status') === $scope.listStatus[i].VIS) {
                        $scope.usuario.nome = $scope.listStatus[i].VIS +":"+ $cookies.get('user');
                        break;
                    }
                    if ( $cookies.get('status') === "null") {
                        $scope.usuario.nome = $scope.listStatus[i].VIS +":"+ $cookies.get('user');
                        break;
                    }
                }
                
            }
        });
    };
    
    
    $scope.exibeNoticia = function () {
        console.log("Noticia.")
        $http({method: 'GET',
            url: urlBase + "acessologin/noticia/" + $cookies.get('cookie')
        }).then(function (response) {
            console.log(response.data);
            if ((response.data.length === 0)) {
                $scope.listNoti.conteudo = "Sem noticia";
            } else {
                $scope.listNoti = response.data;
            }
        });
    };
    
    $scope.selecionaUsuario = function (userSelect) {
        
        console.log(userSelect);
        $scope.cad = userSelect;
        for (var i = 0; i < $scope.listStatus.length; i++) {
            console.log("Olá");
            console.log($cookies.get('status'));
            if ( $cookies.get('status') === $scope.listStatus[0].VIS || $cookies.get('status') === "null") {
                $scope.formFunc = false; 
                $scope.formStatus = false;
                $scope.formCadastro = false;
                $scope.formExibicao = true;
                return ;
            }

            if ( $cookies.get('status') === $scope.listStatus[0].ADM) { 
                console.log("É um ADM");
                $scope.formFunc = true; 
                $scope.formStatus = true;
                $scope.formCadastro = true;
                $scope.formExibicao = false;
                break;
            }
            if ( $cookies.get('status') === $scope.listStatus[0].FUN) {
                console.log("É um funcionario");
                $scope.formFunc = false; 
                $scope.formStatus = false;
                $scope.formCadastro = true;
                $scope.formExibicao = false;
                break;
            }
        }
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


