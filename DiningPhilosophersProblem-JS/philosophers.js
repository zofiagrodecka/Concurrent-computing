console.log("My program");
// Teoria Współbieżnośi, implementacja problemu 5 filozofów w node.js
// Opis problemu: http://en.wikipedia.org/wiki/Dining_philosophers_problem
//   https://pl.wikipedia.org/wiki/Problem_ucztuj%C4%85cych_filozof%C3%B3w
// 1. Dokończ implementację funkcji podnoszenia widelca (Fork.acquire).
// 2. Zaimplementuj "naiwny" algorytm (każdy filozof podnosi najpierw lewy, potem
//    prawy widelec, itd.).
// 3. Zaimplementuj rozwiązanie asymetryczne: filozofowie z nieparzystym numerem
//    najpierw podnoszą widelec lewy, z parzystym -- prawy. 
// 4. Zaimplementuj rozwiązanie z kelnerem (według polskiej wersji strony)
// 5. Zaimplementuj rozwiążanie z jednoczesnym podnoszeniem widelców:
//    filozof albo podnosi jednocześnie oba widelce, albo żadnego.
// 6. Uruchom eksperymenty dla różnej liczby filozofów i dla każdego wariantu
//    implementacji zmierz średni czas oczekiwania każdego filozofa na dostęp 
//    do widelców. Wyniki przedstaw na wykresach.

var Fork = function() {
    this.state = 0;
    return this;
}

Fork.prototype.acquire = function(cb) {
    // zaimplementuj funkcję acquire, tak by korzystala z algorytmu BEB
    // (http://pl.wikipedia.org/wiki/Binary_Exponential_Backoff), tzn:
    // 1. przed pierwszą próbą podniesienia widelca Filozof odczekuje 1ms
    // 2. gdy próba jest nieudana, zwiększa czas oczekiwania dwukrotnie
    //    i ponawia próbę, itd.
    
    var fork = this,
	/*acquire_fork = function (waitingTime) {
        if (fork.state == 0) {
            fork.state = 1;
            cb();
        }
        else {
            setTimeout(acquire_fork, (waitingTime * 2));
        }
	},*/
    delay = 1;

    function acquire_fork() {
        if(fork.state == 0){
            fork.state = 1;
		    cb();
        }
        else {
            delay *= 2;
            setTimeout(acquire_fork, delay);
        }
    }

    setTimeout(acquire_fork, delay);

    //loop(1); // jednomilisekundowy timeout na początek*/
    //setTimeout(acquire_fork, 1);
}

Fork.prototype.release = function() { 
    this.state = 0; 
}

var Philosopher = function(id, forks) {
    this.id = id;
    this.forks = forks;
    this.f1 = id % forks.length;
    this.f2 = (id+1) % forks.length;
    this.MIN_EATING_TIME = 10;
    this.MAX_EATING_TIME = 30;
    return this;
}

Philosopher.prototype.startNaive = function(count) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id,

    // zaimplementuj rozwiązanie naiwne
    // każdy filozof powinien 'count' razy wykonywać cykl
    // podnoszenia widelców -- jedzenia -- zwalniania widelców

    loop = function(count){
        //console.log("ID: " + id + ". Count: " + count);
        if(count > 0){
            forks[f1].acquire(function() {
                console.log('ID: ' + id + '. Raised left knife');
                forks[f2].acquire(function(){
                    console.log('ID: ' + id + '. Raised right knife');
                    setTimeout(function(){
                        console.log("ID: " + id + " finished eating.");
                        forks[f1].release();
                        forks[f2].release();
                        loop(count-1);
                    }, Math.floor(Math.random() * (this.MAX_EATING_TIME - this.MIN_EATING_TIME + 1) + this.MIN_EATING_TIME));
                })
            });
        }
    };

    // Każdy filozof jest startowany w losowym momencie, żeby nie zakleszczyły się od razu
    setTimeout(loop, Math.floor(Math.random() * 10), count);    
}

Philosopher.prototype.startAsym = function(count) {
    var forks = this.forks,
        f1 = this.id % 2 == 0 ? this.f2 : this.f1,
        f2 = this.id % 2 == 0 ? this.f1 : this.f2,
        id = this.id,
    
    // zaimplementuj rozwiązanie asymetryczne
    // każdy filozof powinien 'count' razy wykonywać cykl
    // podnoszenia widelców -- jedzenia -- zwalniania widelców

    releaseForks = function(){
        console.log("ID: " + id + " finished eating.");
        forks[f1].release();
        forks[f2].release();
    },

    loop = function(count){
        if(count > 0){
            forks[f1].acquire(function() {
                if(id % 2 == 0){
                    console.log('ID: ' + id + '. Has right knife');
                }
                else{
                    console.log('ID: ' + id + '. Has left knife');
                }
                forks[f2].acquire(function(){
                    console.log('ID: ' + id + '. Raised right knife');
                    setTimeout(function(){
                        releaseForks();
                        loop(count-1);
                    }, Math.floor(Math.random() * (this.MAX_EATING_TIME - this.MIN_EATING_TIME + 1) + this.MIN_EATING_TIME));
                })
            });
        }
    };

    setTimeout(loop, 0, count);
}

var Waiter = function(begin_state) {
    this.state = begin_state;
    this.eatingPhilosophers = [];
    this.waitingPhilosophers = [];
    return this;
}

Waiter.prototype.acquire = function(philosopherID, cb){
    if(this.state > 0){
        this.eatingPhilosophers.push(philosopherID);
    }
    else{

    }
}

Philosopher.prototype.startConductor = function(count, waiter) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        waiter = this.waiter;
        id = this.id;
    
    // zaimplementuj rozwiązanie z kelnerem
    // każdy filozof powinien 'count' razy wykonywać cykl
    // podnoszenia widelców -- jedzenia -- zwalniania widelców

    releaseForks = function(){
        forks[f1].release();
        forks[f2].release();
        console.log("ID: " + id + " finished eating.");
    }

    loop = function(count){
        if(count > 0){
            waiter.acquire()
            forks[f1].acquire(function() {
                console.log('ID:' + id + '. Has left knife');
                forks[f2].acquire(function(){
                    console.log('ID:' + id + '. Has right knife');
                    setTimeout(function(){
                        releaseForks();
                        loop(count-1);
                    }, 1);
                })
            });
        }
    };

    setTimeout(loop, 1, count);
}


// TODO: wersja z jednoczesnym podnoszeniem widelców
// Algorytm BEB powinien obejmować podnoszenie obu widelców, 
// a nie każdego z osobna

function acquireSimult(fork1, fork2, cb) {
    // zaimplementuj funkcję acquire, tak by korzystala z algorytmu BEB
    // (http://pl.wikipedia.org/wiki/Binary_Exponential_Backoff), tzn:
    // 1. przed pierwszą próbą podniesienia widelca Filozof odczekuje 1ms
    // 2. gdy próba jest nieudana, zwiększa czas oczekiwania dwukrotnie
    //    i ponawia próbę, itd.

    var loopSimult = function(waitingTime){
        if(fork1.state == 0 && fork2.state == 0){
            fork1.state = 1;
            fork2.state = 1;
            cb();
        }
        else{
            setTimeout(acquireSimult, waitingTime*2);
        }
    }

    setTimeout(loopSimult, 1);
    /*var loop = function (waitTime) {
        setTimeout(function () {
            if (fork1.state == 0 && fork2.state == 0) {
            fork1.state = fork2.state = 1;
            cb();
            }
            else
            loop (waitTime * 2);
        }, waitTime);
        };
    
        loop(1); // jednomilisekundowy timeout na początek*/
}

Philosopher.prototype.startSimult = function(count){
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id,

    releaseForks = function(){
        forks[f1].release();
        forks[f2].release();
        console.log("ID: " + this.id + " finished eating.");
    }

    loop = function(count){
        if(count > 0){
            acquireSimult(this.f1, this.f2, function(){
                console.log('ID: ' + this.id + '. Starts eating');
                setTimeout(function(){
                    releaseForks();
                    loop(count-1);
                }, Math.floor(Math.random() * (this.MAX_EATING_TIME - this.MIN_EATING_TIME + 1) + this.MIN_EATING_TIME));
            })
        }
    }

    loopSimult = function (count) {
	    if (count > 0)
		acquireSimult(forks[f1], forks[f2], function () {
		    console.log("filozof " + id + " wziął widelce i zaczyna jeść");
		    setTimeout(function () {
			forks[f1].release();
			forks[f2].release();
			console.log("filozof " + id + " kończy jeść");
			// zakładamy, że myślenie jest reprezentowane przez pierwszy,
			// jednomilisekundowy timeout w acquire2()
			loopSimult(count - 1);
		    }, 1) // zakładamy, że jedzenie trwa 1 milisekundę
		})
	};

    setTimeout(loopSimult, 0, count);
}


var N = 5;
var meals = 10;
var forks = [];
var philosophers = []
for (var i = 0; i < N; i++) {
    forks.push(new Fork());
}

for (var i = 0; i < N; i++) {
    philosophers.push(new Philosopher(i, forks));
}

for (var i = 0; i < N; i++) {
    //philosophers[i].startNaive(meals);
    philosophers[i].startAsym(meals);
    //philosophers[i].startSimult(10);
}