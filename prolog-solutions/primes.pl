init(M) :- sieve(2, -1, M), !.
sieve(M, N, Ma) :- M * M + N * M > Ma, !.
sieve(M, N, Ma) :- N = -1, \+ co(M), Mm is M + 1, sieve(M, 0, Ma), sieve(Mm, -1, Ma), !.
sieve(M, N, Ma) :- N = -1, Mm is M + 1, sieve(Mm, -1, Ma), !.
sieve(M, N, Ma) :- N > -1, \+ co(M), NN is M * M + N * M, \+ co(NN), Nm is N + 1, sieve(M, Nm, Ma), assert(co(NN)), assert(sieve(NN, -1, Ma)), !.
sieve(M, N, Ma) :- N > -1, Nm is N + 1, sieve(M, Nm, Ma), !.
prime(N) :- N > 1,  \+ composite(N).
composite(N) :- N > 1, co(N).
d(1, T, CURR, T) :- true, !.
d(N, T, CURR, R) :-
    NCURR is CURR,
    NN is (N // NCURR),
	0 is mod(N, NCURR),
	append([NCURR], T, R1),
	d(NN, R1, NCURR, R), !.
d(N, T, CURR, R) :-
	d(N, T, CURR + 1, R), !.
prime_divisors(N, T) :- d(N, [], 2, R), reverse(R, Rev), Rev = T.
square_divisors(N, D) :- N2 is N * N, prime_divisors(N2, D).