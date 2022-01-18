use oza;

create table MobileDevice(
MobileDeviceHash varchar(80) primary key not null
);

create table MobileDevice_TestResult(
ID int primary key not null auto_increment,
MobileDeviceHash varchar(80) not null,
TestHash varchar(80) not null,
foreign key (MobileDeviceHash) references MobileDevice(MobileDeviceHash),
foreign key (TestHash) references TestResult(TestHash )
);

create table TestResult(
TestHash varchar(80) primary key not null,
TestResult_Date date,
Result boolean
);

create table Contact(
ContactID int primary key not null auto_increment,
Contact_Date date,
Contact_Duration int(11),
Boolean_Result boolean,
MobileDeviceHash varchar(80) not null,
ContactDeviceHash varchar(80) not null,
foreign key (MobileDeviceHash) references MobileDevice(MobileDeviceHash),
foreign key (ContactDeviceHash) references MobileDevice(MobileDeviceHash)
);

