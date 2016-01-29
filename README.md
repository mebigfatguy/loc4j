# loc4j
an ant task to count lines of code

Probably the second program ever written was a line counting program. It's a natural desire, to be proud of one's accomplishments.
And how do you communicate that to someone who doesn't share your joy for the code itself. Line Counts! It's a statistic older
than dirt.

The truth is, it's a bogus statistic. Whoever came up with the idea, (and who did so seriously) was deranged. Line counts really
mean nothing. I can have the same program with an order of magnitude difference in line counts just by how i lay out my program.

And yet.... people... your boss.... still .. want .. them.

Why, I have no idea. Line counts as a metric should be abolished. Maybe code size (bytes) is better, or maybe some other metrics. But
line counts? NO.

And yet here you are wondering if this ant task will work for you. Will it produce the right answers? Because Hell, your BOSS WANTS THE DAME LINE COUNT NUMBERS.

So is this program right? Does it count right? 

Who the hell knows. 

It does count them, somehow, and it is consistent, you should get the same values each time. But what does it mean to do line counts, anyway?

Do you count white space? comments? braces? lines that tumble onto following lines? Annotations? Fields? Ascii Art? 

How about unit tests, utility classes to install stuff? Validation programs? Ant tasks?

Again, who know what THEY want. So you pick something, and say THIS IS IT. THIS IS THE CORRECT VALUE!


OK, so how does loc4j work. Well it's kind of different. it actually doesn't look at source code at all.

It takes the route that javac knows best. It uses the line number tables in the class file format to figure out how many 
lines of code you have. That means it skips all the obvious stuff you want skipped. But it also means things like an 
uninitialized local variable declaration isn't counted. Should it? Again, who the hell knows. But that's what this does.

So to run, you point it at class path roots, either jars or directories, and say go to town. As long as your class files 
were compiled with -g (debug) it will count consistently and reliably. You can get Line, Method and Class counts.

To use, you build an ant task like this

    <task name="count" xmlns:loc4j="antlib:com.mebigfatguy.loc4j">
    
        <loc4j:loc4j linesProperty="__lines__" methodsProperty="__methods__" classesProperties="__classes__">
			<classpath id="myjars">
				<pathelement location="${lib.dir}/some-precious.jar" />
				<pathelement location="${lib.dir}/some-other-precious.jar" />
           </classpath>
    </task>

And it will populate the ant properties specified.

You don't have to specify all three, just the one(s) you want.

You can then do something like

    <echo message="Number of Lines Gosh Golly is ${__lines__} I'M SO PROUD"/>

enjoy, and yes i know it's annoyingly similarly named to log4j... heheheheeheh

I'll get it up on maven somepoint soon, so that the one of you who actually tries to use it doesn't have a hard time.
Give me a sec. Cheepers.
