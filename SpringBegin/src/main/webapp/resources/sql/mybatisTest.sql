create table spring_mybatistest
(no number
,name varchar2(20)
,email varchar2(30)
,tel varchar2(20)
,addr varchar2(200)
,writeday date default sysdate
,constraint PK_spring_mybatistest_no primary key(no)
);

create sequence seq_mybatistest
start with 1
increment by 1
nominvalue
nomaxvalue
nocycle
nocache;

select*
from spring_mybatistest;


------------------------------------------------------------------------------------------

create table spring_mybatistest2
(num number
,nickname varchar2(20)
,email varchar2(30)
,telephone varchar2(20)
,address varchar2(200)
,registerday date default sysdate
,constraint PK_spring_mybatistest2_num primary key(num)
);

create sequence seq_mybatistest2
start with 1
increment by 1
nominvalue
nomaxvalue
nocycle
nocache;

select*
from spring_mybatistest2;

-------------------------------------------------------------------------------------------

alter table spring_mybatistest
add birthday varchar2(20);

select *
from spring_mybatistest
order by no desc;


select no, name, email, tel, addr, 
       to_char(writeday, 'yyyy-mm-dd hh24:mi:ss') AS writeday, 
       nvl(birthday, ' ') AS birthday
from spring_mybatistest
where name like'%' || '정화' || '%'
and to_char(writeday, 'yyyy-mm-dd') >= '2018-06-04' and trunc(writeday) <= '2018-06-06';



select *
from myorauser.spring_mybatistest
order by no desc;


update hr.employees set last_name = '이순신'
where employee_id = 100;


---------------------------------------------------------------------------------------------------------
department_id department_name employee_id name  jubun fun_gender(jubun) fun_age(jubun)  yearpayment



select *
from hr.employees


select nvl(D.department_id, '9999') as department_id, nvl(D.department_name, '인턴') as department_name, employee_id, first_name || last_name, jubun,
       case when substr(jubun, 7, 1) in ('1', '3') then '남' else '여' end as gender,
       extract(year from sysdate) - ( to_number(substr(jubun, 1, 2)) + case when substr(jubun, 7, 1) in('1', '2') then 1900 else 2000 end) + 1 as age,
       (salary + salary * nvl(commission_pct, 0)) * 12 as yearpay
from hr.employees E left join hr.departments D
on E.department_id = D.department_id;



select *
from myorauser.spring_mybatistest
order by no desc;

show user;

select *
from spring_mybatistest
order by no desc;

select *
from hr.departments;
 
select *
from hr.employees;

update hr.employees set last_name = '이순신'
where employee_id = 100;

rollback;

---------------------------------------------------------------------------------------------------------------
 department_id   department_name   employee_id   ename  jubun  fun_gender(jubun)  fun_age(jubun)   yearpay
 
 
 create or replace function fun_gender
 (p_jubun IN varchar2)
 return varchar2
 is
    v_result  varchar2(4);
 begin
      select case when substr(p_jubun,7,1) in('1','3') 
             then '남' else '여' end
             into v_result 
      from dual;

      return v_result;
 end fun_gender; 
 
 
 select fun_gender('8706071234567')
 from dual;
 
 select fun_gender('8706072234567')
 from dual;
 
 select fun_gender('0106073234567')
 from dual;
 
 select fun_gender('0106074234567')
 from dual;
 
 
 create or replace function fun_age
 (p_jubun IN varchar2)
 return number
 is
    v_result  number(3);
 begin
      select extract(year from sysdate) - decode(substr(p_jubun,7,1), '1', to_number( substr(p_jubun,1,2) )+1900
                                                                    , '2', to_number( substr(p_jubun,1,2) )+1900
                                                                         , to_number( substr(p_jubun,1,2) )+2000) + 1
             into v_result                                                            
      from dual;                                                                         

      return v_result;
 end fun_age;
 
 
 select fun_age('8706071234567')
 from dual;
 
 select fun_age('0106073234567')
 from dual;

commit;


select E.department_id,
       D.department_name,
       E.employee_id,
       E.first_name || E.last_name as ename,
       E.jubun,
       fun_gender(E.jubun) as gender,
       fun_age(E.jubun) as age,
       nvl(E.salary + E.commission_pct * E.salary, E.salary) * 12 as yearpay
from hr.departments D right join hr.employees E
on D.department_id = E.department_id
where nvl(E.department_id, -9999) = 50;



select distinct nvl(department_id, -9999) as department_id
from hr.employees
order by department_id desc;



select E.department_id,   
		       D.department_name,   
		       E.employee_id,
		       E.first_name || ' '  || E.last_name AS ename,
		       E.jubun,
		       fun_gender(E.jubun) AS gender,
		       fun_age(E.jubun) AS age,
		       nvl(E.salary + E.commission_pct*E.salary, E.salary)*12 AS yearpay
		from hr.departments D right join hr.employees E 
		on D.department_id = E.department_id
		where 1=1
		and nvl(E.department_id, -9999) in (-9999,30,80,50)
		and fun_gender(E.jubun) = '남'
    and trunc(fun_age(E.jubun), -1)



select fun_gender(jubun) as gender,
       count(*) as cnt,
       round(count(*) / (select count(*) from hr.employees) * 100, 1) as percent
from hr.employees
group by fun_gender(jubun)



select decode( trunc( fun_age(jubun), -1 ), 0, '10대 미만', to_char( trunc( fun_age(jubun), -1 ) ) ||'대' ) as ageline,
       count(*) as CNT      
from hr.employees
group by trunc( fun_age(jubun), -1 )
order by trunc( fun_age(jubun), -1 )



select nvl(to_char(E.department_id), '-9999') as deptno,
       nvl(D.department_name, '부서없음') as deptname,
       trunc( avg(nvl(E.salary + (E.salary * E.commission_pct), E.salary)) ) * 12 AS salary
from hr.departments D right join hr.employees E
on D.department_id = E.department_id
group by D.department_name, E.department_id
order by E.department_id






