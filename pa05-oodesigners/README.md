# OODesigners:tm: Bullet Journal
Make bullet journaling easier with the OODesigners's new Bullet Journal! The new Bullet Journal comes with the ability to add new events or tasks, with limitations! In addition, you can open and save your new bujo journal in any file you want. You are also able to customize your layout with our new feature Quotes and Notes: the ability to add a new inspirational quote or little reminders. Overall, with our cute design and sleek interface, keeping track of your life has never been easier.

#### How To Run OODesigner's Bullet Journal
- Run the entry-point main method in the Driver class to run OODesigner's Bullet Journal.
- When you first start OODesigner's Bullet Journal application, a Splash Screen will be displayed.
- After, you will be prompted to open a file. You can open an existing .bujo file, or you can click "Cancel" to make a new Bullet Journal calendar.
- Explore the calendar and customize it to how you wish. Feel free to add tasks, events, quotes, and/or notes!
- When you're done, make sure to click the Save button (or press command/control + s) to save your OODesigner Bullet Journal to a .bujo file to open at another time.
- You can open previously created .bujo files by clicking the Open button (or press command/control + o) and selecting the file you wish to open.

#### Other Important Notes
- For the full experience of OODesigner's calendar display, please download the [Mali Regular font](https://fonts.google.com/specimen/Mali) (this is not required).
- Make sure to update the max number of tasks and/or events per day before adding tasks and events to your bujo calendar, otherwise you will not be able to create new calendar objects.

#### Sample OODesigners:tm: Bullet Journal:
![UpdatedGUI](https://github.com/CS-3500-OOD/pa05-oodesigners/assets/119459688/14628da4-a3f7-458a-98da-b1f47e413578)

#### SOLID Principles:
###### Single Responsibility:
- We made sure to keep every one of our classes separate to others in terms of functionality. For example, we have a seperate class to deal with converting the JSon file back and forth to be read, a seperate class for creating JavaFX features, and other model classes to represent different data definitions. In this way, each class only has one "job" or topic to take care of.

###### Open-Closed:
- Our code represents the idea of "open for extension, closed for modification". This is because we constantly make use of abstraction in our code (for example, using List instead of a specific List, as well as Readable or Map instead of StringReader or HashMap). In addition, with out Abstract Calendar Object class, it is open for extension because many classes can extend that class or implement the underlying Calendar Object Interface. In this way we can extend the Calendar Object Interface to support more types of objects (not just tasks and events) but we cannot modify the interface from including a name or description.

###### Liskov Substitution:
- Our objects representing Events and Tasks can easily replace both their superclasses: including both the abstract class and the interface, because they both contain the information that their superclass "contracts" specify.

###### Interface Segregation:
- Our objects representing Events and Tasks can easily replace both their superclasses: including both the abstract class and the interface, because they both contain the information that their superclass "contracts" specify.

###### Dependency Inversion:
- Our controller depends on abstractions to promote little dependency. For example, our view is passed into the controller as well as our proxy converter to have as little dependency as possible. In this way both the Proxy Converter and the View can exist outside the Controller and would therefore allow this project to be used in higher, more complex ways.

### How We Would Extend Our Program:
- We would extend our program by creating more calendar objects, such as holidays and meeting times. To implement this, we would create new classes: Holiday.java and Meeting.java. With our design and use of the Open-Closed Principle, we will easily be able to extend the CalendarObject class in both of these classes and expand on each specific object's attributes from there.

### Attributions:
- [journal.gif](https://giphy.com/stickers/november-journaling-foopklo-xix1onOcFoBdLHjWaS)

Splash Screen:
![SplashScreen](https://github.com/CS-3500-OOD/pa05-oodesigners/assets/119459688/1ec157d4-6ca1-4eaa-a711-5efd4eda473c)

Week GUI View Screenshot (Alpha Release):
<img width="1393" alt="GUIScreenshot" src="https://github.com/CS-3500-OOD/pa05-oodesigners/assets/119459688/887ac6d3-719f-4ed3-a05f-7cd35d6d74f8">
