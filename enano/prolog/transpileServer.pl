:- use_module(library(http/thread_httpd)).
:- use_module(library(http/http_dispatch)).
:- use_module(library(http/http_json)).
:- use_module(library(http/http_log)).
:- use_module(library(http/http_client)).

% URL handlers.
:- http_handler('/', handle_request, [method(post)]).

% Calculates a + b.

writeBody(B):- open('NewFile.no',write,Out),write(Out,B),close(Out).

handle_request(Request) :- http_read_data(Request, Data,[]), writeBody(Data),reply_json_dict([]).

server(Port) :- http_server(http_dispatch, [port(Port)]).

%set_setting(http:logfile, 'service_log_file.log').

:- initialization
    format('*** Starting Server ***~n', []),
    (current_prolog_flag(argv, [SPort | _]) -> true ; SPort='8000'),
    atom_number(SPort, Port),
    format('*** Serving on port ~d *** ~n', [Port]),
    server(Port).