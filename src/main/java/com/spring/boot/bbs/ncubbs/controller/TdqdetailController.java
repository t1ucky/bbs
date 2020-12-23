package com.spring.boot.bbs.ncubbs.controller;

import com.spring.boot.bbs.ncubbs.domain.*;
import com.spring.boot.bbs.ncubbs.domain.User;
import com.spring.boot.bbs.ncubbs.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;


/*
author xingyunting
 */

@RestController
@RequestMapping("/tdqdetail")
public class TdqdetailController {
    @Autowired
    private TreplyRepository TreplyRepository;
    @Autowired
    private DreplyRepository dreplyRepository;
    @Autowired
    private QreplyRepository qreplyRepository;
    @Autowired
    private TopicRepository TopicRepository;
    @Autowired
    private  DemandRepository demandRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private MainController mainController;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QcommentRepository qcommentRepository;
    @Autowired
    private TcommentRepository tcommentRepository;
    @Autowired
    private DcommentRepository dcommentRepository;
    @Autowired
    private FollowRepository followRepository;


    @GetMapping("/topicdetail")
    public ModelAndView createReply() {
        return new ModelAndView("tdqdetail/topicdetail");
    }

    /**
     * 发表评论
     * @param rtcontents
     * @return
     */
    @PostMapping(path = "/topicdetail/fabiao")
    public String createComment(
                                @RequestParam(value="rtcontents",required=true)String rtcontents,HttpSession session) {
        System.out.println("1312");
        User user = (User)session.getAttribute("registeruser");
        Topic topic=(Topic)session.getAttribute("mytopic");
        Long tid=topic.getTid();
        mainController.createReplytopic(tid,rtcontents,session);
        System.out.println(tid+""+rtcontents);
        return "1";
    }

    @GetMapping("topicdetail/{tid}")
    public ModelAndView showdetail(@PathVariable("tid")Long tid,Model model,HttpSession session){
        Topic topic = TopicRepository.getTopicByTid(tid);
        session.setAttribute("mytopic", topic);
        topic.setTclickcount(topic.getTclickcount()+1);
        TopicRepository.saveAndFlush(topic);
        model.addAttribute("commentreplylist",tcommentRepository.findAll());
        model.addAttribute("topic",topic);
        model.addAttribute("userlist",userRepository.findAll());
        model.addAttribute("replylist",TreplyRepository.findAll());
        model.addAttribute("usernow",(User)session.getAttribute("registeruser"));
      //  model.addAttribute("replytopiclist",TopicRepository.findAll());
        return new ModelAndView("tdqdetail/topicdetail","TopicModel",model);
    }

    @PostMapping(path = "/topicdetail/fabiao/comment")
    public String createCommentTopic(@RequestParam(value="ctcontents",required=true)String ctcontents,HttpSession session){
        User user = (User)session.getAttribute("registeruser");
        ReplyTopic replyTopic =(ReplyTopic)session.getAttribute("myreplytopic");
        Long rtid = replyTopic.getRtid();
        System.out.print("tqdcontrol");
        System.out.print(rtid+""+ctcontents+"tdqcontroller");
        mainController.createCommenttopic(rtid,ctcontents,session);
        return "1";
    }
    @PostMapping(path = "/questiondetail/fabiao/comment")
    public String createCommentQuestion( @RequestParam(value="ctcontents",required=true)String ctcontents,HttpSession session){
        System.out.print("tqdcontrol");
        ReplyQuestion replyQuestion = (ReplyQuestion) session.getAttribute("myreplyquestion");
        Long rqid =replyQuestion.getRqid();
        mainController.createCommentquestion(rqid,ctcontents,session);
        return "1";
    }

    @PostMapping(path = "/demanddetail/fabiao/comment")
    public String createCommentDemand( @RequestParam(value="ctcontents",required=true)String ctcontents,HttpSession session){
        System.out.print("tqdcontrol");
        ReplyDemand replyDemand = (ReplyDemand)session.getAttribute("myreplydemand");
        Long rdid = replyDemand.getRdid();
        //ReplyTopic replyTopic = (ReplyTopic)session.getAttribute("myreplytopic");
        mainController.createCommentdemand(rdid,ctcontents,session);
        return "1";
    }


    @GetMapping("topicreplydetail/{rtid}")
    public ModelAndView replytopicdetail(@PathVariable("rtid")Long rtid,HttpSession session,Model model){
        System.out.print(rtid);
        Topic topic = (Topic) session.getAttribute("mytopic");
        ReplyTopic replyTopic = TreplyRepository.getReplyTopicByRtid(rtid);
        session.setAttribute("myreplytopic",replyTopic);
        model.addAttribute("commentreplylist",tcommentRepository.findAll());
        model.addAttribute("topic",topic);
        model.addAttribute("userlist",userRepository.findAll());
        model.addAttribute("replylist",TreplyRepository.findAll());
        model.addAttribute("usernow",(User)session.getAttribute("registeruser"));
        return new ModelAndView("tdqdetail/topicdetail","TopicModel",model);
    }
    @GetMapping("topicreplyfellow/{rtid}")
    public ModelAndView replytopicfellow(@PathVariable("rtid")Long rtid,HttpSession session,Model model){
        System.out.print(rtid);
        Topic topic = (Topic) session.getAttribute("mytopic");
        ReplyTopic replyTopic = TreplyRepository.getReplyTopicByRtid(rtid);
        User user1=(User)session.getAttribute("registeruser");
        Long uid1= user1.getUid();
        Long uid2=replyTopic.getUid();
        Follow fellow =new Follow(uid1,uid2);
        followRepository.save(fellow);System.out.print("sucess");
        model.addAttribute("commentreplylist",tcommentRepository.findAll());
        model.addAttribute("topic",topic);
        model.addAttribute("userlist",userRepository.findAll());
        model.addAttribute("replylist",TreplyRepository.findAll());
        model.addAttribute("usernow",(User)session.getAttribute("registeruser"));
        return new ModelAndView("tdqdetail/topicdetail","TopicModel",model);
    }

    @GetMapping("like/{tid}")
    public ModelAndView liketopicdetail(@PathVariable("tid")Long tid,HttpSession session,Model model){
        System.out.print(tid);
        Topic topic = (Topic) session.getAttribute("mytopic");
        topic.setTlikecount(topic.getTlikecount()+1);
        TopicRepository.saveAndFlush(topic);
        model.addAttribute("commentreplylist",tcommentRepository.findAll());
        model.addAttribute("topic",topic);
        model.addAttribute("userlist",userRepository.findAll());
        model.addAttribute("replylist",TreplyRepository.findAll());
        model.addAttribute("usernow",(User)session.getAttribute("registeruser"));
        return new ModelAndView("tdqdetail/topicdetail","TopicModel",model);
    }
/*
    @PostMapping(path="/tdconfim")
    public String listComments(@RequestParam(value="tid",required=true) Long tid, Model model, HttpSession session) {
        Topic topic = TopicRepository.getTopicByTid(tid);
        List<ReplyTopic> rtcomments = topic.getTcomments();
        System.out.println("1212");
            // 判断操作用户是否是评论的所有者
            String rtcommentOwner = "";
            if (session.getAttribute("user") != null && session.getAttribute("user").equals(rtcomments)) {
                User principal = (User) session.getAttribute("user");
                if (principal != null) {
                    rtcommentOwner = principal.getUname();
                }
            }
        model.addAttribute("rtcommentOwner", rtcommentOwner);
        model.addAttribute("rtcomments", rtcomments);
        return "../admin/login";
    }*/
    /**
     * 删除评论
     * @return
     */
    /*
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteBlog(@PathVariable("id") Long id, Long blogId) {

        boolean isOwner = false;
        User user = TreplyRepository.getRtcommentsById(id).getUtid();

        // 判断操作用户是否是博客的所有者
        if (SecurityContextHolder.getContext().getAuthentication() !=null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                &&  !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
            User principal = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal !=null && user.getUsername().equals(principal.getUsername())) {
                isOwner = true;
            }
        }

        if (!isOwner) {
            return ResponseEntity.ok().body(new Response(false, "没有操作权限"));
        }

        try {
            blogService.removeComment(blogId, id);
            commentService.removeComment(id);
        } catch (ConstraintViolationException e)  {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }

        return ResponseEntity.ok().body(new Response(true, "处理成功", null));
    }
*/ @GetMapping("/demanddetail")
    public ModelAndView createReplyDemand() {
        return new ModelAndView("tdqdetail/demanddetail");
    }

    /**
     * 发表评论
     * @param rtcontents
     * @return
     */@PostMapping(path = "/demanddetail/fabiao")
    public String createReplyDemand(@RequestParam(value="rtcontents",required=true)String rtcontents,HttpSession session) {
        System.out.println("1312");
        User user = (User)session.getAttribute("registeruser");
        Demand demand=(Demand) session.getAttribute("mydemand");
        Long did=demand.getDid();
        mainController.createReplydemand(did,rtcontents,session);
        System.out.println(did+""+rtcontents);
        return "1";
    }
    @GetMapping("demanddetail/{did}")
    public ModelAndView showdetaildemand(@PathVariable("did")Long did,Model model,HttpSession session){
        Demand demand = demandRepository.getDemandByDid(did);
        session.setAttribute("mydemand", demand);
        demand.setDclickcount(demand.getDclickcount()+1);
        demandRepository.saveAndFlush(demand);
       // TopicRepository.saveAndFlush(topic);
       // model.addAttribute("commentreplylist",TreplyRepository.findAll());
        model.addAttribute("commentreplylistdemand",dreplyRepository.findAll());
        model.addAttribute("demand",demand);
        model.addAttribute("userlist",userRepository.findAll());
        model.addAttribute("usernow",(User)session.getAttribute("registeruser"));
        return new ModelAndView("tdqdetail/demanddetail","DemandModel",model);
    }

    @GetMapping("demandreplydetail/{rdid}")
    public ModelAndView replydemanddetail(@PathVariable("rdid")Long rdid,HttpSession session,Model model){
        System.out.print(rdid);
        Demand demand = (Demand)session.getAttribute("mydemand");
       // Topic topic = (Topic) session.getAttribute("mytopic");
        //ReplyTopic replyTopic = TreplyRepository.getReplyTopicByRtid(rtid);
        ReplyDemand replyDemand = dreplyRepository.getReplyDemandByRdid(rdid);
        session.setAttribute("myreplydemand",replyDemand);
        model.addAttribute("commentreplylistdemand",dreplyRepository.findAll());
        model.addAttribute("demand",demand);
        model.addAttribute("userlist",userRepository.findAll());
        model.addAttribute("usernow",(User)session.getAttribute("registeruser"));
        return new ModelAndView("tdqdetail/demanddetail","DemandModel",model);
    }

    @GetMapping("likedemand/{did}")
    public ModelAndView likedemanddetail(@PathVariable("did")Long did,HttpSession session,Model model){
        System.out.print(did);
        Demand demand= (Demand)session.getAttribute("mydemand");
        //Topic topic = (Topic) session.getAttribute("mytopic");
        demand.setDlikecount(demand.getDlikecount()+1);
        //topic.setTlikecount(topic.getTlikecount()+1);
        demandRepository.saveAndFlush(demand);
        model.addAttribute("commentreplylistdemand",dreplyRepository.findAll());
        model.addAttribute("demand",demand);
        model.addAttribute("userlist",userRepository.findAll());
        model.addAttribute("usernow",(User)session.getAttribute("registeruser"));
        return new ModelAndView("tdqdetail/demanddetail","DemandModel",model);
    }

//question

    @GetMapping("/questiondetail")
    public ModelAndView createReplyQuestion() {
        return new ModelAndView("tdqdetail/demanddetail");
    }

    /**
     * 发表评论
     * @param rtcontents
     * @return
     */@PostMapping(path = "/questiondetail/fabiao")
    public String createReplyQuestion(@RequestParam(value="rtcontents",required=true)String rtcontents,HttpSession session) {
        System.out.println("1312");
        User user = (User)session.getAttribute("registeruser");
        if(user==null)
            return "0";
        else {
            Question question =(Question)session.getAttribute("myquestion");
            Long qid=question.getQid();
            mainController.createReplyquestion(qid,rtcontents,session);
            System.out.println(qid+""+rtcontents);
            return "1";
        }

    }
    @GetMapping("questiondetail/{qid}")
    public ModelAndView showdetailquestioon(@PathVariable("qid")Long qid,Model model,HttpSession session){
         Question question=questionRepository.getQuestionByQid(qid);
        session.setAttribute("myquestion", question);
        model.addAttribute("question",question);
        question.setQclickcount(question.getQclickcount()+1);
        questionRepository.saveAndFlush(question);
        //demandRepository.saveAndFlush(demand);
       // model.addAttribute("commentreplylistdemand",dreplyRepository.findAll());
        model.addAttribute("commentreplylistquestion",qreplyRepository.findAll());
        model.addAttribute("userlist",userRepository.findAll());
        model.addAttribute("usernow",(User)session.getAttribute("registeruser"));
        return new ModelAndView("tdqdetail/questiondetail","QuestionModel",model);
    }

    @GetMapping("questionreplydetail/{rqid}")
    public ModelAndView replyquestiondetail(@PathVariable("rqid")Long rqid,HttpSession session,Model model){
        System.out.print(rqid);
        //Demand demand = (Demand)session.getAttribute("mydemand");
        Question question = (Question)session.getAttribute("myquestion");
        ReplyQuestion replyQuestion  = qreplyRepository.getReplyQuestionsByRqid(rqid);
        //ReplyDemand replyDemand = dreplyRepository.getReplyDemandByRdid(rdid);
        session.setAttribute("myreplyquestion",replyQuestion);
        model.addAttribute("question",question);
        model.addAttribute("commentreplylistquestion",qreplyRepository.findAll());
        model.addAttribute("userlist",userRepository.findAll());
        model.addAttribute("usernow",(User)session.getAttribute("registeruser"));
        return new ModelAndView("tdqdetail/questiondetail","QuestionModel",model);
    }

    @GetMapping("likequestion/{qid}")
    public ModelAndView likequestiondetail(@PathVariable("qid")Long qid,HttpSession session,Model model){
        System.out.print(qid);
       // Demand demand= (Demand)session.getAttribute("mydemand");
        Question question = (Question)session.getAttribute("myquestion");
        question.setQlikecount(question.getQlikecount()+1);
        //demand.setDlikecount(demand.getDlikecount()+1);
        //topic.setTlikecount(topic.getTlikecount()+1);
        //demandRepository.saveAndFlush(demand);
        questionRepository.saveAndFlush(question);
        model.addAttribute("question",question);
        model.addAttribute("userlist",userRepository.findAll());
        model.addAttribute("commentreplylistquestion",qreplyRepository.findAll());
        model.addAttribute("usernow",(User)session.getAttribute("registeruser"));
        return new ModelAndView("tdqdetail/questiondetail","QuestionModel",model);
    }
}
