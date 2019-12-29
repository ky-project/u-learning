/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2008                    */
/* Created on:     2019-2-16 20:37:10                           */
/*==============================================================*/


if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('DoExperiment') and o.name = 'FK_DOEXPERI_EXPERIMEN_EXPERIME')
alter table DoExperiment
   drop constraint FK_DOEXPERI_EXPERIMEN_EXPERIME
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('DoExperiment') and o.name = 'FK_DOEXPERI_STUDENTDO_STUDENT')
alter table DoExperiment
   drop constraint FK_DOEXPERI_STUDENTDO_STUDENT
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('Documentation') and o.name = 'FK_DOCUMENT_HOLDDOCUM_COURSE')
alter table Documentation
   drop constraint FK_DOCUMENT_HOLDDOCUM_COURSE
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('ExaminationResult') and o.name = 'FK_EXAMINAT_CORRESPON_EXAMININ')
alter table ExaminationResult
   drop constraint FK_EXAMINAT_CORRESPON_EXAMININ
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('ExaminationResult') and o.name = 'FK_EXAMINAT_MAPPINGRE_QUESTION')
alter table ExaminationResult
   drop constraint FK_EXAMINAT_MAPPINGRE_QUESTION
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('ExaminationTask') and o.name = 'FK_EXAMINAT_HOLDEXAMI_TEACHING')
alter table ExaminationTask
   drop constraint FK_EXAMINAT_HOLDEXAMI_TEACHING
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('Examining') and o.name = 'FK_EXAMININ_EXAMINATI_EXAMINAT')
alter table Examining
   drop constraint FK_EXAMININ_EXAMINATI_EXAMINAT
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('Examining') and o.name = 'FK_EXAMININ_STUDENTEX_STUDENT')
alter table Examining
   drop constraint FK_EXAMININ_STUDENTEX_STUDENT
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('Experiment') and o.name = 'FK_EXPERIME_HOLDEXPER_COURSE')
alter table Experiment
   drop constraint FK_EXPERIME_HOLDEXPER_COURSE
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('Notice') and o.name = 'FK_NOTICE_RELEASENO_TEACHING')
alter table Notice
   drop constraint FK_NOTICE_RELEASENO_TEACHING
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('Question') and o.name = 'FK_QUESTION_HOLDQUEST_COURSE')
alter table Question
   drop constraint FK_QUESTION_HOLDQUEST_COURSE
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('Resource') and o.name = 'FK_RESOURCE_HOLDRESOU_COURSE')
alter table Resource
   drop constraint FK_RESOURCE_HOLDRESOU_COURSE
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('Studying') and o.name = 'FK_STUDYING_STUDENTBE_STUDENT')
alter table Studying
   drop constraint FK_STUDYING_STUDENTBE_STUDENT
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('Studying') and o.name = 'FK_STUDYING_TEACHINGT_TEACHING')
alter table Studying
   drop constraint FK_STUDYING_TEACHINGT_TEACHING
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('TeachingTask') and o.name = 'FK_TEACHING_HOLDTASK_COURSE')
alter table TeachingTask
   drop constraint FK_TEACHING_HOLDTASK_COURSE
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('TeachingTask') and o.name = 'FK_TEACHING_UNDERTAKE_TEACHER')
alter table TeachingTask
   drop constraint FK_TEACHING_UNDERTAKE_TEACHER
go

if exists (select 1
            from  sysobjects
           where  id = object_id('Course')
            and   type = 'U')
   drop table Course
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('DoExperiment')
            and   name  = 'StudentDoingExperiment_FK'
            and   indid > 0
            and   indid < 255)
   drop index DoExperiment.StudentDoingExperiment_FK
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('DoExperiment')
            and   name  = 'ExperimentBeingDone_FK'
            and   indid > 0
            and   indid < 255)
   drop index DoExperiment.ExperimentBeingDone_FK
go

if exists (select 1
            from  sysobjects
           where  id = object_id('DoExperiment')
            and   type = 'U')
   drop table DoExperiment
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('Documentation')
            and   name  = 'HoldDocumentation_FK'
            and   indid > 0
            and   indid < 255)
   drop index Documentation.HoldDocumentation_FK
go

if exists (select 1
            from  sysobjects
           where  id = object_id('Documentation')
            and   type = 'U')
   drop table Documentation
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('ExaminationResult')
            and   name  = 'CorrespondingResult_FK'
            and   indid > 0
            and   indid < 255)
   drop index ExaminationResult.CorrespondingResult_FK
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('ExaminationResult')
            and   name  = 'MappingResult_FK'
            and   indid > 0
            and   indid < 255)
   drop index ExaminationResult.MappingResult_FK
go

if exists (select 1
            from  sysobjects
           where  id = object_id('ExaminationResult')
            and   type = 'U')
   drop table ExaminationResult
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('ExaminationTask')
            and   name  = 'HoldExaminationTask_FK'
            and   indid > 0
            and   indid < 255)
   drop index ExaminationTask.HoldExaminationTask_FK
go

if exists (select 1
            from  sysobjects
           where  id = object_id('ExaminationTask')
            and   type = 'U')
   drop table ExaminationTask
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('Examining')
            and   name  = 'StudentExamining_FK'
            and   indid > 0
            and   indid < 255)
   drop index Examining.StudentExamining_FK
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('Examining')
            and   name  = 'ExaminationTaskBeingExamined_FK'
            and   indid > 0
            and   indid < 255)
   drop index Examining.ExaminationTaskBeingExamined_FK
go

if exists (select 1
            from  sysobjects
           where  id = object_id('Examining')
            and   type = 'U')
   drop table Examining
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('Experiment')
            and   name  = 'HoldExperiment_FK'
            and   indid > 0
            and   indid < 255)
   drop index Experiment.HoldExperiment_FK
go

if exists (select 1
            from  sysobjects
           where  id = object_id('Experiment')
            and   type = 'U')
   drop table Experiment
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('Notice')
            and   name  = 'ReleaseNotice_FK'
            and   indid > 0
            and   indid < 255)
   drop index Notice.ReleaseNotice_FK
go

if exists (select 1
            from  sysobjects
           where  id = object_id('Notice')
            and   type = 'U')
   drop table Notice
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('Question')
            and   name  = 'HoldQuestion_FK'
            and   indid > 0
            and   indid < 255)
   drop index Question.HoldQuestion_FK
go

if exists (select 1
            from  sysobjects
           where  id = object_id('Question')
            and   type = 'U')
   drop table Question
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('Resource')
            and   name  = 'HoldResource_FK'
            and   indid > 0
            and   indid < 255)
   drop index Resource.HoldResource_FK
go

if exists (select 1
            from  sysobjects
           where  id = object_id('Resource')
            and   type = 'U')
   drop table Resource
go

if exists (select 1
            from  sysobjects
           where  id = object_id('Student')
            and   type = 'U')
   drop table Student
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('Studying')
            and   name  = 'StudentBeStudying_FK'
            and   indid > 0
            and   indid < 255)
   drop index Studying.StudentBeStudying_FK
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('Studying')
            and   name  = 'TeachingTaskBeingStudied_FK'
            and   indid > 0
            and   indid < 255)
   drop index Studying.TeachingTaskBeingStudied_FK
go

if exists (select 1
            from  sysobjects
           where  id = object_id('Studying')
            and   type = 'U')
   drop table Studying
go

if exists (select 1
            from  sysobjects
           where  id = object_id('Teacher')
            and   type = 'U')
   drop table Teacher
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('TeachingTask')
            and   name  = 'UndertakeTask_FK'
            and   indid > 0
            and   indid < 255)
   drop index TeachingTask.UndertakeTask_FK
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('TeachingTask')
            and   name  = 'HoldTask_FK'
            and   indid > 0
            and   indid < 255)
   drop index TeachingTask.HoldTask_FK
go

if exists (select 1
            from  sysobjects
           where  id = object_id('TeachingTask')
            and   type = 'U')
   drop table TeachingTask
go

/*==============================================================*/
/* Table: Course                                                */
/*==============================================================*/
create table Course (
   CourseID             bigint               not null,
   CourseNumber         varchar(8)           not null,
   CourseName           varchar(50)          not null,
   CourseCredit         smallint             not null,
   CourseMemo           varchar(255)         null,
   constraint PK_COURSE primary key nonclustered (CourseID)
)
go

/*==============================================================*/
/* Table: DoExperiment                                          */
/*==============================================================*/
create table DoExperiment (
   DoExperimentID       bigint               not null,
   ExperimentID         bigint               null,
   StudentID            bigint               null,
   ExperimentCommitState bit                  not null,
   ExperimentCommitTime datetime             null,
   ExperimentResult     text                 null,
   ExperimentURL        varchar(255)         null,
   ExperimentScore      float                null,
   ExperimentAdvice     varchar(255)         null,
   DoExperimentMemo     varchar(255)         null,
   constraint PK_DOEXPERIMENT primary key nonclustered (DoExperimentID)
)
go

/*==============================================================*/
/* Index: ExperimentBeingDone_FK                                */
/*==============================================================*/
create index ExperimentBeingDone_FK on DoExperiment (
ExperimentID ASC
)
go

/*==============================================================*/
/* Index: StudentDoingExperiment_FK                             */
/*==============================================================*/
create index StudentDoingExperiment_FK on DoExperiment (
StudentID ASC
)
go

/*==============================================================*/
/* Table: Documentation                                         */
/*==============================================================*/
create table Documentation (
   DocumentationID      bigint               not null,
   CourseID             bigint               null,
   DocumentationTitle   varchar(255)         not null,
   DocumentationSummary varchar(1024)        null,
   DocumentationImageURL varchar(1024)        null,
   DocumentationAttachment varchar(1024)        null,
   DocumentationCategory varchar(255)         not null,
   DocumentationShared  bit                  not null,
   DocumentationMemo    varchar(255)         null,
   constraint PK_DOCUMENTATION primary key nonclustered (DocumentationID)
)
go

/*==============================================================*/
/* Index: HoldDocumentation_FK                                  */
/*==============================================================*/
create index HoldDocumentation_FK on Documentation (
CourseID ASC
)
go

/*==============================================================*/
/* Table: ExaminationResult                                     */
/*==============================================================*/
create table ExaminationResult (
   ExaminationResultID  bigint               not null,
   QuestionID           bigint               null,
   ExaminingID          bigint               null,
   StudentAnswer        text                 null,
   StudentScore         float                null,
   ExaminationResultMemo varchar(255)         null,
   constraint PK_EXAMINATIONRESULT primary key nonclustered (ExaminationResultID)
)
go

/*==============================================================*/
/* Index: MappingResult_FK                                      */
/*==============================================================*/
create index MappingResult_FK on ExaminationResult (
QuestionID ASC
)
go

/*==============================================================*/
/* Index: CorrespondingResult_FK                                */
/*==============================================================*/
create index CorrespondingResult_FK on ExaminationResult (
ExaminingID ASC
)
go

/*==============================================================*/
/* Table: ExaminationTask                                       */
/*==============================================================*/
create table ExaminationTask (
   ExaminationTaskID    bigint               not null,
   TeachingTaskID       bigint               null,
   ExaminationDuration  smallint             not null,
   ExaminationState     smallint             not null,
   ExaminationParameters varchar(1024)        not null,
   ExaminationShowResult bit                  not null,
   ExaminationMemo      varchar(255)         null,
   constraint PK_EXAMINATIONTASK primary key nonclustered (ExaminationTaskID)
)
go

/*==============================================================*/
/* Index: HoldExaminationTask_FK                                */
/*==============================================================*/
create index HoldExaminationTask_FK on ExaminationTask (
TeachingTaskID ASC
)
go

/*==============================================================*/
/* Table: Examining                                             */
/*==============================================================*/
create table Examining (
   ExaminingID          bigint               not null,
   StudentID            bigint               null,
   ExaminationTaskID    bigint               null,
   ExaminingHostIP      varchar(150)         not null,
   ExaminingLoginTime   datetime             not null,
   ExaminingRemainTime  smallint             not null,
   ExaminingState       smallint             not null,
   ExaminingStateSwitchTime datetime             not null,
   ExaminingMemo        varchar(255)         null,
   constraint PK_EXAMINING primary key nonclustered (ExaminingID)
)
go

/*==============================================================*/
/* Index: ExaminationTaskBeingExamined_FK                       */
/*==============================================================*/
create index ExaminationTaskBeingExamined_FK on Examining (
ExaminationTaskID ASC
)
go

/*==============================================================*/
/* Index: StudentExamining_FK                                   */
/*==============================================================*/
create index StudentExamining_FK on Examining (
StudentID ASC
)
go

/*==============================================================*/
/* Table: Experiment                                            */
/*==============================================================*/
create table Experiment (
   ExperimentID         bigint               not null,
   CourseID             bigint               null,
   ExperimentOrder      smallint             not null,
   ExperimentTitle      varchar(255)         not null,
   ExperimentContent    text                 null,
   ExperimentAttachment varchar(1024)        null,
   ExperimentMemo       varchar(255)         null,
   constraint PK_EXPERIMENT primary key nonclustered (ExperimentID)
)
go

/*==============================================================*/
/* Index: HoldExperiment_FK                                     */
/*==============================================================*/
create index HoldExperiment_FK on Experiment (
CourseID ASC
)
go

/*==============================================================*/
/* Table: Notice                                                */
/*==============================================================*/
create table Notice (
   NoticeID             bigint               not null,
   TeachingTaskID       bigint               null,
   NoticeTitle          varchar(255)         not null,
   NoticeContent        text                 null,
   NoticeAttachment     varchar(1024)        null,
   NoticeShared         bit                  not null,
   NoticePostTime       datetime             not null,
   NoticeKeywords       varchar(255)         null,
   NoticeMemo           varchar(255)         null,
   constraint PK_NOTICE primary key nonclustered (NoticeID)
)
go

/*==============================================================*/
/* Index: ReleaseNotice_FK                                      */
/*==============================================================*/
create index ReleaseNotice_FK on Notice (
TeachingTaskID ASC
)
go

/*==============================================================*/
/* Table: Question                                              */
/*==============================================================*/
create table Question (
   QuestionID           bigint               not null,
   CourseID             bigint               null,
   QuestionText         text                 not null,
   QuestionImageURL     varchar(1024)        null,
   QuestionKey          text                 null,
   QuestionKnowledge    varchar(50)          not null,
   QuestionType         varchar(10)          not null,
   constraint PK_QUESTION primary key nonclustered (QuestionID)
)
go

/*==============================================================*/
/* Index: HoldQuestion_FK                                       */
/*==============================================================*/
create index HoldQuestion_FK on Question (
CourseID ASC
)
go

/*==============================================================*/
/* Table: Resource                                              */
/*==============================================================*/
create table Resource (
   ResourceID           bigint               not null,
   CourseID             bigint               null,
   ResourceTitle        varchar(255)         not null,
   ResourceSummary      varchar(1024)        null,
   ResourceURL          varchar(1024)        not null,
   ResourceType         smallint             not null,
   ResourceShared       bit                  not null,
   ResourceMemo         varchar(255)         null,
   constraint PK_RESOURCE primary key nonclustered (ResourceID)
)
go

/*==============================================================*/
/* Index: HoldResource_FK                                       */
/*==============================================================*/
create index HoldResource_FK on Resource (
CourseID ASC
)
go

/*==============================================================*/
/* Table: Student                                               */
/*==============================================================*/
create table Student (
   StudentID            bigint               not null,
   StudentNumber        varchar(20)          not null,
   StudentName          varchar(10)          not null,
   StudentGender        varchar(2)           not null,
   StudentClass         varchar(50)          not null,
   StudentPhone         varchar(20)          null,
   StudentEmail         varchar(50)          null,
   StudentMemo          varchar(255)         null,
   constraint PK_STUDENT primary key nonclustered (StudentID)
)
go

/*==============================================================*/
/* Table: Studying                                              */
/*==============================================================*/
create table Studying (
   StudyingID           bigint               not null,
   TeachingTaskID       bigint               null,
   StudentID            bigint               null,
   StudyingMemo         varchar(255)         null,
   constraint PK_STUDYING primary key nonclustered (StudyingID)
)
go

/*==============================================================*/
/* Index: TeachingTaskBeingStudied_FK                           */
/*==============================================================*/
create index TeachingTaskBeingStudied_FK on Studying (
TeachingTaskID ASC
)
go

/*==============================================================*/
/* Index: StudentBeStudying_FK                                  */
/*==============================================================*/
create index StudentBeStudying_FK on Studying (
StudentID ASC
)
go

/*==============================================================*/
/* Table: Teacher                                               */
/*==============================================================*/
create table Teacher (
   TeacherID            bigint               not null,
   TeacherNumber        varchar(10)          not null,
   TeacherName          varchar(10)          not null,
   TeacherTitle         varchar(20)          not null,
   TeacherPhone         varchar(20)          null,
   TeacherEmail         varchar(50)          null,
   TeacherPhoto         varchar(1024)        null,
   TeacherMemo          varchar(255)         null,
   constraint PK_TEACHER primary key nonclustered (TeacherID)
)
go

/*==============================================================*/
/* Table: TeachingTask                                          */
/*==============================================================*/
create table TeachingTask (
   TeachingTaskID       bigint               not null,
   CourseID             bigint               null,
   TeacherID            bigint               null,
   TeachingTaskAlias    varchar(255)         not null,
   Term                 varchar(50)          not null,
   TeachingTaskIsValid  bit                  not null,
   TeachingTaskMemo     varchar(255)         null,
   constraint PK_TEACHINGTASK primary key nonclustered (TeachingTaskID)
)
go

/*==============================================================*/
/* Index: HoldTask_FK                                           */
/*==============================================================*/
create index HoldTask_FK on TeachingTask (
CourseID ASC
)
go

/*==============================================================*/
/* Index: UndertakeTask_FK                                      */
/*==============================================================*/
create index UndertakeTask_FK on TeachingTask (
TeacherID ASC
)
go

alter table DoExperiment
   add constraint FK_DOEXPERI_EXPERIMEN_EXPERIME foreign key (ExperimentID)
      references Experiment (ExperimentID)
go

alter table DoExperiment
   add constraint FK_DOEXPERI_STUDENTDO_STUDENT foreign key (StudentID)
      references Student (StudentID)
go

alter table Documentation
   add constraint FK_DOCUMENT_HOLDDOCUM_COURSE foreign key (CourseID)
      references Course (CourseID)
go

alter table ExaminationResult
   add constraint FK_EXAMINAT_CORRESPON_EXAMININ foreign key (ExaminingID)
      references Examining (ExaminingID)
go

alter table ExaminationResult
   add constraint FK_EXAMINAT_MAPPINGRE_QUESTION foreign key (QuestionID)
      references Question (QuestionID)
go

alter table ExaminationTask
   add constraint FK_EXAMINAT_HOLDEXAMI_TEACHING foreign key (TeachingTaskID)
      references TeachingTask (TeachingTaskID)
go

alter table Examining
   add constraint FK_EXAMININ_EXAMINATI_EXAMINAT foreign key (ExaminationTaskID)
      references ExaminationTask (ExaminationTaskID)
go

alter table Examining
   add constraint FK_EXAMININ_STUDENTEX_STUDENT foreign key (StudentID)
      references Student (StudentID)
go

alter table Experiment
   add constraint FK_EXPERIME_HOLDEXPER_COURSE foreign key (CourseID)
      references Course (CourseID)
go

alter table Notice
   add constraint FK_NOTICE_RELEASENO_TEACHING foreign key (TeachingTaskID)
      references TeachingTask (TeachingTaskID)
go

alter table Question
   add constraint FK_QUESTION_HOLDQUEST_COURSE foreign key (CourseID)
      references Course (CourseID)
go

alter table Resource
   add constraint FK_RESOURCE_HOLDRESOU_COURSE foreign key (CourseID)
      references Course (CourseID)
go

alter table Studying
   add constraint FK_STUDYING_STUDENTBE_STUDENT foreign key (StudentID)
      references Student (StudentID)
go

alter table Studying
   add constraint FK_STUDYING_TEACHINGT_TEACHING foreign key (TeachingTaskID)
      references TeachingTask (TeachingTaskID)
go

alter table TeachingTask
   add constraint FK_TEACHING_HOLDTASK_COURSE foreign key (CourseID)
      references Course (CourseID)
go

alter table TeachingTask
   add constraint FK_TEACHING_UNDERTAKE_TEACHER foreign key (TeacherID)
      references Teacher (TeacherID)
go

