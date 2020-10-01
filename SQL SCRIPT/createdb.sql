-- create database team1;
use team1;
SET FOREIGN_KEY_CHECKS=0;
SET sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));

/*
-Responsibility-

DBCOURSE_Restaurant : 김해리, 박소연, 신유진
DBCOURSE_Ingredient : 김해리, 신유진
DBCOURSE_Food : 김해리, 박소연, 배소연, 신유진
DBCOURSE_Employee : 김해리, 박소연, 배소연, 신유진
DBCOURSE_Evaluation : 김해리, 박소연, 신유진
DBCOURSE_Address : 김해리, 박소연, 배소연, 신유진
DBCOURSE_Delivery : 김해리, 신유진
DBCOURSE_Service : 김해리, 박소연, 신유진
DBCOURSE_Order : 김해리, 배소연, 신유진
DBCOURSE_Inventory : 김해리, 박소연, 신유진

DBCOURSE_Delivery_O : 김해리, 배소연, 신유진
DBCOURSE_Delivery_X : 김해리, 배소연, 신유진
DBCOURSE_Order_Ingredient : 배소연

create index : 신유진

dropdb.sql : 신유진
*/

create table DBCOURSE_Restaurant (
    name varchar(20) primary key,
    type varchar(20) not null,
    phone_number varchar(15) not null,
    profit integer(10) not null);
     
insert into DBCOURSE_Restaurant values ('Nolboo', 'Oriental', '02-557-3848', 0);
insert into DBCOURSE_Restaurant values ('SchoolFood', 'Oriental', '02-510-5005', 0);
insert into DBCOURSE_Restaurant values ('Abiko', 'Oriental', '02-514-3212', 0);
insert into DBCOURSE_Restaurant values ('Yummy Sushi', 'Oriental', '02-545-0023', 0);
insert into DBCOURSE_Restaurant values ('Outback', 'Western', '02-558-4491', 0);
insert into DBCOURSE_Restaurant values ('VIPS', 'Western', '02-3442-2997', 0);
insert into DBCOURSE_Restaurant values ('Ewhasung', 'Oriental', '02-393-8511', 0);
insert into DBCOURSE_Restaurant values ('BillyAngel', 'Dessert', '02-512-6257', 0);
insert into DBCOURSE_Restaurant values ('Sulbing', 'Dessert', '02-518-1415', 0);
insert into DBCOURSE_Restaurant values ('KFC', 'Western', '02-547-1664', 0);
insert into DBCOURSE_Restaurant values ('Shake Shack', 'Western', '02-510-5217', 0);

create table DBCOURSE_Food (
    name varchar(20) primary key,
    type varchar(20) not null,
    price integer(10) not null,
    restaurant_name varchar(20) not null,
    foreign key(restaurant_name) references DBCOURSE_Restaurant(name));
   
insert into DBCOURSE_Food values ('bossam', 'Oriental', 15000, 'Nolboo');
insert into DBCOURSE_Food values ('budae-jjigae', 'Oriental', 8000, 'Nolboo');
insert into DBCOURSE_Food values ('wheat-tteokbokki','Oriental', 4000, 'SchoolFood');
insert into DBCOURSE_Food values ('rice-tteokbokki','Oriental',4000, 'SchoolFood');
insert into DBCOURSE_Food values ('tuna gimbap', 'Oriental',3000, 'SchoolFood');
insert into DBCOURSE_Food values ('mushroom curry rice', 'Oriental',8000, 'Abiko');
insert into DBCOURSE_Food values ('pork curry rice', 'Oriental',8000, 'Abiko');
insert into DBCOURSE_Food values ('salmon roll','Oriental',9000, 'Yummy Sushi');
insert into DBCOURSE_Food values ('tuna roll','Oriental',9000, 'Yummy Sushi');
insert into DBCOURSE_Food values ('gyoza','Oriental',6000, 'Yummy Sushi');
insert into DBCOURSE_Food values ('T-bone steak','Western',28000, 'Outback');
insert into DBCOURSE_Food values ('ribs steak','Western',25000, 'Outback');
insert into DBCOURSE_Food values ('Pork-sirloin steak','Western',24000, 'Outback');
insert into DBCOURSE_Food values ('Beef-sirloin steak','Western',27000, 'VIPS');
insert into DBCOURSE_Food values ('tomato pasta','Western',13000, 'VIPS');
insert into DBCOURSE_Food values ('carbonara','Western',12000, 'VIPS');
insert into DBCOURSE_Food values ('seafood pasta','Western',15000, 'VIPS');
insert into DBCOURSE_Food values ('shrimp fried rice','Oriental',11000, 'Ewhasung');
insert into DBCOURSE_Food values ('jjamppong','Oriental',8000, 'Ewhasung');
insert into DBCOURSE_Food values ('tangsuyuk','Oriental',20000, 'Ewhasung');
insert into DBCOURSE_Food values ('jajangmyeon','Oriental',8000, 'Ewhasung');
insert into DBCOURSE_Food values ('tiramisu','Dessert',6000, 'BillyAngel');
insert into DBCOURSE_Food values ('strawberry tart','Dessert',5000, 'BillyAngel');
insert into DBCOURSE_Food values ('cheese cake','Dessert',5000, 'BillyAngel');
insert into DBCOURSE_Food values ('mango snow ice','Dessert',7000, 'Sulbing');
insert into DBCOURSE_Food values ('injeolmi snow ice','Dessert',8000, 'Sulbing');
insert into DBCOURSE_Food values ('chicken burger','Western',8000, 'KFC');
insert into DBCOURSE_Food values ('hot wings','Western',6000, 'KFC');
insert into DBCOURSE_Food values ('grilled chicken','Western',12000, 'KFC');
insert into DBCOURSE_Food values ('shack burger','Western',8000, 'Shake Shack');
insert into DBCOURSE_Food values ('mushroom burger','Western',7000, 'Shake Shack');
insert into DBCOURSE_Food values ('hot dog','Western',6000, 'Shake Shack');
insert into DBCOURSE_Food values ('vanila shakes','Western',5000, 'Shake Shack');

create table DBCOURSE_Ingredient (
    name varchar(20) not null,
    food_name varchar(20) not null,
    type varchar(20) not null,
   primary key(name, food_name),
   foreign key(food_name) references DBCOURSE_Food(name)
);
    
insert into DBCOURSE_Ingredient values ('pork', 'bossam', 'meat');
insert into DBCOURSE_Ingredient values ('pork', 'pork curry rice', 'meat');
insert into DBCOURSE_Ingredient values ('pork', 'ribs steak', 'meat');
insert into DBCOURSE_Ingredient values ('pork', 'Pork-siroin steak', 'meat');
insert into DBCOURSE_Ingredient values ('pork', 'tangsuyuk', 'meat');
insert into DBCOURSE_Ingredient values ('beef', 'T-bone steak','meat');
insert into DBCOURSE_Ingredient values ('beef', 'Beef-siroin steak','meat');
insert into DBCOURSE_Ingredient values ('sausage', 'budae-jjigae', 'meat');
insert into DBCOURSE_Ingredient values ('sausage', 'hot dog', 'meat');
insert into DBCOURSE_Ingredient values ('bacon', 'shack burger', 'meat');
insert into DBCOURSE_Ingredient values ('tuna', 'tuna gimbap', 'seafood');
insert into DBCOURSE_Ingredient values ('tuna', 'tuna roll', 'seafood');
insert into DBCOURSE_Ingredient values ('milk', 'carbonara', 'dairy');
insert into DBCOURSE_Ingredient values ('milk', 'mango snow ice', 'dairy');
insert into DBCOURSE_Ingredient values ('milk', 'injeolmi snow ice', 'dairy');
insert into DBCOURSE_Ingredient values ('milk', 'vanila shakes', 'dairy');
insert into DBCOURSE_Ingredient values ('shrimp', 'seafood pasta', 'seafood');
insert into DBCOURSE_Ingredient values ('shrimp', 'shrimp fried rice', 'seafood');
insert into DBCOURSE_Ingredient values ('shrimp', 'jjamppong', 'seafood');
insert into DBCOURSE_Ingredient values ('salmon', 'salmon roll', 'seafood');
insert into DBCOURSE_Ingredient values ('mushroom', 'mushroom curry rice', 'fungus');
insert into DBCOURSE_Ingredient values ('mushroom', 'carbonara', 'fungus');
insert into DBCOURSE_Ingredient values ('mushroom', 'mushroom burger', 'fungus');
insert into DBCOURSE_Ingredient values ('tomato', 'tomato pasta', 'vegetable');
insert into DBCOURSE_Ingredient values ('carrot', 'tuna gimbap', 'vegetable');
insert into DBCOURSE_Ingredient values ('kimchi', 'bossam', 'vegetable');
insert into DBCOURSE_Ingredient values ('kimchi', 'budae-jjigae', 'vegetable');
insert into DBCOURSE_Ingredient values ('chicken', 'chicken burger', 'meat');
insert into DBCOURSE_Ingredient values ('chicken', 'hot wings', 'meat');
insert into DBCOURSE_Ingredient values ('chicken', 'grilled chicken', 'meat');
insert into DBCOURSE_Ingredient values ('rice', 'rice-tteokbokki', 'grain');
insert into DBCOURSE_Ingredient values ('rice', 'tuna gimbap', 'grain');
insert into DBCOURSE_Ingredient values ('rice', 'mushroom curry rice', 'grain');
insert into DBCOURSE_Ingredient values ('rice', 'pork curry rice', 'grain');
insert into DBCOURSE_Ingredient values ('rice', 'salmon roll', 'grain');
insert into DBCOURSE_Ingredient values ('rice', 'tuna roll', 'grain');
insert into DBCOURSE_Ingredient values ('rice', 'shrimp fried rice', 'grain');
insert into DBCOURSE_Ingredient values ('rice', 'injeolmi snow ice', 'grain');
insert into DBCOURSE_Ingredient values ('wheat', 'wheat-tteokbokki', 'grain');
insert into DBCOURSE_Ingredient values ('wheat', 'gyoza', 'grain');
insert into DBCOURSE_Ingredient values ('wheat', 'tomato pasta', 'grain');
insert into DBCOURSE_Ingredient values ('wheat', 'carbonara', 'grain');
insert into DBCOURSE_Ingredient values ('wheat', 'seafood pasta', 'grain');
insert into DBCOURSE_Ingredient values ('wheat', 'jjamppong', 'grain');
insert into DBCOURSE_Ingredient values ('wheat', 'jajangmyeon', 'grain');
insert into DBCOURSE_Ingredient values ('wheat', 'strawberry tart', 'grain');
insert into DBCOURSE_Ingredient values ('wheat', 'cheese cake', 'grain');
insert into DBCOURSE_Ingredient values ('wheat', 'chicken burger', 'grain');
insert into DBCOURSE_Ingredient values ('wheat', 'shack burger', 'grain');
insert into DBCOURSE_Ingredient values ('wheat', 'mushroom burger', 'grain');
insert into DBCOURSE_Ingredient values ('wheat', 'hot dog', 'grain');
insert into DBCOURSE_Ingredient values ('squid', 'seafood pasta', 'seafood');
insert into DBCOURSE_Ingredient values ('squid', 'jjamppong', 'seafood');
insert into DBCOURSE_Ingredient values ('strawberry', 'strawberry tart', 'fruit');
insert into DBCOURSE_Ingredient values ('cheese', 'cheese cake', 'dairy');
insert into DBCOURSE_Ingredient values ('mango', 'mango snow ice', 'fruit');
insert into DBCOURSE_Ingredient values ('lettuce', 'chicken burger', 'vegetable');
insert into DBCOURSE_Ingredient values ('lettuce', 'shack burger', 'vegetable');
insert into DBCOURSE_Ingredient values ('lettuce', 'mushroom burger', 'vegetable');
 
create table DBCOURSE_Employee (
    id char(5) primary key,
    name varchar(20) not null,
    restaurant_name varchar(20) not null,
    position varchar(20) not null,
    salary integer(10) not null,
    foreign key(restaurant_name) references DBCOURSE_Restaurant(name));

insert into DBCOURSE_Employee values ('10001','Jake Peralta', 'Outback', 'chef', 102000);
insert into DBCOURSE_Employee values ('10002','Amy Santiago', 'Outback', 'cook', 83000);
insert into DBCOURSE_Employee values ('10003','Charles Boyle', 'Outback', 'manager', 35000);
insert into DBCOURSE_Employee values ('10004','Terry Jeffords', 'Outback', 'server', 40000);
insert into DBCOURSE_Employee values ('10005','Jessica Pearson', 'VIPS', 'chef', 95000);
insert into DBCOURSE_Employee values ('10006','Harvey Specter', 'VIPS', 'cook', 80000);
insert into DBCOURSE_Employee values ('10007','Donna Paulsen', 'VIPS', 'manager', 55000);
insert into DBCOURSE_Employee values ('10008','Michael Ross', 'VIPS', 'server', 30000);
insert into DBCOURSE_Employee values ('10009','Harry Potter', 'KFC', 'cook', 40000);
insert into DBCOURSE_Employee values ('10010','Ron Weasley', 'KFC', 'manager', 25000);
insert into DBCOURSE_Employee values ('10011','Walter O\'Brien', 'Shake Shack', 'cook', 50000);
insert into DBCOURSE_Employee values ('10012','Happy Quinn', 'Shake Shack', 'manager', 35000);
insert into DBCOURSE_Employee values ('10013','Iron Man', 'BillyAngel', 'cook', 36000);
insert into DBCOURSE_Employee values ('10014','Thor Odinson', 'BillyAngel', 'manager', 25000);
insert into DBCOURSE_Employee values ('10015','Minji Kim', 'Nolboo', 'cook', 40000);
insert into DBCOURSE_Employee values ('10016','Gildong Hong', 'Nolboo', 'manager', 38000);
insert into DBCOURSE_Employee values ('10017','Bogum Park', 'Nolboo', 'server', 27000);
insert into DBCOURSE_Employee values ('10018','Seodong Bae', 'SchoolFood', 'cook', 60000);
insert into DBCOURSE_Employee values ('10019','Hyesoo Kim', 'SchoolFood', 'manager', 40000);
insert into DBCOURSE_Employee values ('10020','Hashimoto Kanna', 'Abiko', 'cook', 62000);
insert into DBCOURSE_Employee values ('10021','Ninomiya Kazunari', 'Abiko', 'manager', 48000);
insert into DBCOURSE_Employee values ('10022','Abe Shinzo', 'Abiko', 'server', 5000);
insert into DBCOURSE_Employee values ('10023','Ishihara Satomi', 'Yummy Sushi', 'chef', 35000);
insert into DBCOURSE_Employee values ('10024','Sakurai Sho', 'Yummy Sushi', 'manager', 30000);
insert into DBCOURSE_Employee values ('10025','Liu Yifei', 'Ewhasung', 'cook', 30000);
insert into DBCOURSE_Employee values ('10026','Li Bingbing', 'Ewhasung', 'manager', 25000);
insert into DBCOURSE_Employee values ('10027','Xi Jinping', 'Ewhasung', 'server', 15000);
insert into DBCOURSE_Employee values ('10028','Olaf', 'Sulbing', 'cook', 23000);
insert into DBCOURSE_Employee values ('10029','Elsa', 'Sulbing', 'manager', 37000);

create table DBCOURSE_Evaluation (
    restaurant_name varchar(20) not null,
    score integer(10) not null,
    recommendation varchar(20) not null,
    foreign key(restaurant_name) references DBCOURSE_Restaurant(name),
    primary key(restaurant_name, score, recommendation));
    
insert into DBCOURSE_Evaluation values ('Nolboo', 8, 'family');
insert into DBCOURSE_Evaluation values ('Nolboo', 5, 'family');
insert into DBCOURSE_Evaluation values ('Nolboo', 10, 'friends');
insert into DBCOURSE_Evaluation values ('SchoolFood', 2, 'family');
insert into DBCOURSE_Evaluation values ('SchoolFood', 1, 'date');
insert into DBCOURSE_Evaluation values ('Abiko', 2, 'friends');
insert into DBCOURSE_Evaluation values ('Abiko', 2, 'family');
insert into DBCOURSE_Evaluation values ('Abiko', 1, 'friends');
insert into DBCOURSE_Evaluation values ('Yummy Sushi', 10, 'date');
insert into DBCOURSE_Evaluation values ('Yummy Sushi', 8, 'family');
insert into DBCOURSE_Evaluation values ('Yummy Sushi', 6, 'date');
insert into DBCOURSE_Evaluation values ('Yummy Sushi', 9, 'friends');
insert into DBCOURSE_Evaluation values ('Outback', 10, 'date');
insert into DBCOURSE_Evaluation values ('Outback', 5, 'friends');
insert into DBCOURSE_Evaluation values ('VIPS', 3, 'date');
insert into DBCOURSE_Evaluation values ('VIPS', 5, 'friends');
insert into DBCOURSE_Evaluation values ('Ewhasung', 6, 'friends');
insert into DBCOURSE_Evaluation values ('Ewhasung', 9, 'family');
insert into DBCOURSE_Evaluation values ('Ewhasung', 5, 'friends');
insert into DBCOURSE_Evaluation values ('BillyAngel', 7, 'date');
insert into DBCOURSE_Evaluation values ('BillyAngel', 9, 'date');
insert into DBCOURSE_Evaluation values ('BillyAngel', 3, 'friends');
insert into DBCOURSE_Evaluation values ('Sulbing', 6, 'friends');
insert into DBCOURSE_Evaluation values ('Sulbing', 4, 'date');
insert into DBCOURSE_Evaluation values ('Sulbing', 5, 'family');
insert into DBCOURSE_Evaluation values ('KFC', 4, 'friends');
insert into DBCOURSE_Evaluation values ('KFC', 3, 'family');
insert into DBCOURSE_Evaluation values ('Shake Shack', 8, 'friends');
insert into DBCOURSE_Evaluation values ('Shake Shack', 7, 'date');
insert into DBCOURSE_Evaluation values ('Shake Shack', 10, 'friends');
insert into DBCOURSE_Evaluation values ('Shake Shack', 8, 'family');

create table DBCOURSE_Address (
    restaurant_name varchar(20) primary key,
    city varchar(10) not null,
    parking char(1),
    foreign key(restaurant_name) references DBCOURSE_Restaurant(name));
    
insert into DBCOURSE_Address values ('Nolboo','Seocho', null);   
insert into DBCOURSE_Address values ('SchoolFood','Jongno', null); 
insert into DBCOURSE_Address values ('Abiko','Jongno','o');  
insert into DBCOURSE_Address values ('Yummy Sushi','Seongdong', null);   
insert into DBCOURSE_Address values ('Outback','Yongsan','o');   
insert into DBCOURSE_Address values ('VIPS','Songpa','x');   
insert into DBCOURSE_Address values ('Ewhasung','Seocho', null);   
insert into DBCOURSE_Address values ('BillyAngel','Gangnam','o');   
insert into DBCOURSE_Address values ('Sulbing','Yongsan', null);  
insert into DBCOURSE_Address values ('KFC','Gangnam', null);   
insert into DBCOURSE_Address values ('Shake Shack','Gangnam','x');

create table DBCOURSE_Delivery (
    restaurant_name varchar(20) primary key,
    deliver char(1) not null,
    delivery_cost integer(10),
    foreign key(restaurant_name) references DBCOURSE_Restaurant(name));

insert into DBCOURSE_Delivery values ('Nolboo', 'o', 2000);
insert into DBCOURSE_Delivery values ('SchoolFood', 'o', 0);
insert into DBCOURSE_Delivery values ('Abiko', 'x', null);
insert into DBCOURSE_Delivery values ('Yummy Sushi', 'o', 1000);
insert into DBCOURSE_Delivery values ('Outback', 'x', null);
insert into DBCOURSE_Delivery values ('VIPS', 'x', null);
insert into DBCOURSE_Delivery values ('Ewhasung', 'o', 0);
insert into DBCOURSE_Delivery values ('BillyAngel', 'x', null);
insert into DBCOURSE_Delivery values ('Sulbing', 'o', 4000);
insert into DBCOURSE_Delivery values ('KFC', 'o', 0);
insert into DBCOURSE_Delivery values ('Shake Shack', 'x', null);
    
create table DBCOURSE_Service (
    restaurant_name varchar(20) not null,
    permission varchar(20) not null,
    foreign key(restaurant_name) references DBCOURSE_Restaurant(name),
    primary key(restaurant_name, permission));

insert into DBCOURSE_Service values ('Abiko','baby');
insert into DBCOURSE_Service values ('Outback','pet');
insert into DBCOURSE_Service values ('Outback','baby');
insert into DBCOURSE_Service values ('VIPS','pet');
insert into DBCOURSE_Service values ('BillyAngel','pet');
insert into DBCOURSE_Service values ('BillyAngel','baby');
insert into DBCOURSE_Service values ('Shake Shack','none');

create table DBCOURSE_Order (
    order_number integer(10) not null,
    restaurant_name varchar(20) not null,
    food_name varchar(20) not null,
    price integer(10) not null,
    foreign key (restaurant_name) references DBCOURSE_Restaurant(name),
    foreign key (food_name) references DBCOURSE_Food(name),
    primary key(order_number));

create table DBCOURSE_Inventory(
    restaurant_name varchar(20) not null,
    ingredient_name varchar(20) not null,
    numIngredient integer(10) not null,
    foreign key(restaurant_name) references DBCOURSE_Restaurant(name),
    foreign key(ingredient_name) references DBCOURSE_Ingredient(name),
    primary key(restaurant_name, ingredient_name));
    
insert into DBCOURSE_Inventory values ('Nolboo', 'kimchi', 10);
insert into DBCOURSE_Inventory values ('Nolboo', 'sausage', 10);
insert into DBCOURSE_Inventory values ('Nolboo', 'pork', 8);
insert into DBCOURSE_Inventory values ('SchoolFood', 'wheat', 13);
insert into DBCOURSE_Inventory values ('SchoolFood', 'rice', 9);
insert into DBCOURSE_Inventory values ('SchoolFood', 'carrot', 1);
insert into DBCOURSE_Inventory values ('SchoolFood', 'tuna', 2);
insert into DBCOURSE_Inventory values ('Abiko', 'mushroom', 3);
insert into DBCOURSE_Inventory values ('Abiko', 'rice', 5);
insert into DBCOURSE_Inventory values ('Abiko', 'pork', 4);
insert into DBCOURSE_Inventory values ('Yummy Sushi', 'salmon', 1);
insert into DBCOURSE_Inventory values ('Yummy Sushi', 'tuna', 3);
insert into DBCOURSE_Inventory values ('Yummy Sushi', 'rice', 10);
insert into DBCOURSE_Inventory values ('Yummy Sushi', 'wheat', 10);
insert into DBCOURSE_Inventory values ('Outback', 'beef', 3);
insert into DBCOURSE_Inventory values ('Outback', 'pork', 5);
insert into DBCOURSE_Inventory values ('VIPS', 'beef', 10);
insert into DBCOURSE_Inventory values ('VIPS', 'wheat', 15);
insert into DBCOURSE_Inventory values ('VIPS', 'tomato', 6);
insert into DBCOURSE_Inventory values ('VIPS', 'milk', 6);
insert into DBCOURSE_Inventory values ('VIPS', 'mushroom', 10);
insert into DBCOURSE_Inventory values ('VIPS', 'shrimp', 7);
insert into DBCOURSE_Inventory values ('VIPS', 'squid', 9);
insert into DBCOURSE_Inventory values ('Ewhasung', 'wheat', 10);
insert into DBCOURSE_Inventory values ('Ewhasung', 'rice', 7);
insert into DBCOURSE_Inventory values ('Ewhasung', 'shrimp', 9);
insert into DBCOURSE_Inventory values ('Ewhasung', 'squid', 0);
insert into DBCOURSE_Inventory values ('Ewhasung', 'pork', 4);
insert into DBCOURSE_Inventory values ('BillyAngel','wheat',10);
insert into DBCOURSE_Inventory values ('BillyAngel','strawberry',8);
insert into DBCOURSE_Inventory values ('BillyAngel','cheese',7);
insert into DBCOURSE_Inventory values ('Sulbing','milk',10);
insert into DBCOURSE_Inventory values ('Sulbing','mango',3);
insert into DBCOURSE_Inventory values ('Sulbing','rice',7);
insert into DBCOURSE_Inventory values ('KFC','chicken',10);
insert into DBCOURSE_Inventory values ('KFC','lettuce',4);
insert into DBCOURSE_Inventory values ('KFC','wheat',8);
insert into DBCOURSE_Inventory values ('Shake Shack','bacon',10);
insert into DBCOURSE_Inventory values ('Shake Shack','lettuce',10);
insert into DBCOURSE_Inventory values ('Shake Shack','wheat',10);
insert into DBCOURSE_Inventory values ('Shake Shack','mushroom',1);
insert into DBCOURSE_Inventory values ('Shake Shack','sausage',10);
insert into DBCOURSE_Inventory values ('Shake Shack','milk',10);

create index restaurant_name_index on dbcourse_restaurant(name);
create index restaurant_city_index on dbcourse_address(city);
create index restaurant_food_index on dbcourse_food(name);
create index food_price_index on dbcourse_food(price);
create index employee_id_index on dbcourse_employee(id);
create index ingredient_index on dbcourse_ingredient(name);

create view DBCOURSE_Delivery_O as
    select DBCOURSE_Restaurant.name as restaurant_name, city,
		   DBCOURSE_Food.type as type, DBCOURSE_Food.name as food_name, price
    from (DBCOURSE_Delivery natural join DBCOURSE_Food,DBCOURSE_Restaurant)
		  natural join DBCOURSE_Address
    where DBCOURSE_Food.restaurant_name = DBCOURSE_Restaurant.name and deliver = 'o'
    group by DBCOURSE_Food.name;

create view DBCOURSE_Delivery_X as
    select DBCOURSE_Restaurant.name as restaurant_name, city,
		   DBCOURSE_Food.type as type, DBCOURSE_Food.name as food_name, price
    from (DBCOURSE_Delivery natural join DBCOURSE_Food,DBCOURSE_Restaurant)
		  natural join DBCOURSE_Address
    where DBCOURSE_Food.restaurant_name = DBCOURSE_Restaurant.name and deliver = 'x'
    group by DBCOURSE_Food.name;

create view DBCOURSE_Order_Ingredient as
    select order_number, DBCOURSE_Order.restaurant_name,
		   DBCOURSE_Order.food_name, DBCOURSE_Ingredient.name as ingredient
   from DBCOURSE_Food, DBCOURSE_Order, DBCOURSE_Ingredient
   where DBCOURSE_Food.restaurant_name = DBCOURSE_Order.restaurant_name
   and DBCOURSE_Food.name = DBCOURSE_Order.food_name
   and DBCOURSE_Food.name = DBCOURSE_Ingredient.food_name;