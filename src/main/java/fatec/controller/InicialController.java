package fatec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import fatec.model.Assinatura;
import fatec.model.User;
import fatec.repository.AssinaturaRepository;
import fatec.repository.UserRepository;

@Controller
public class InicialController {

	private final UserRepository userRepository;
	private final AssinaturaRepository assinaturaRepository;

	@Autowired
	public InicialController(UserRepository userRepository, AssinaturaRepository assinaturaRepository) {
		this.userRepository = userRepository;
		this.assinaturaRepository = assinaturaRepository;
	}

	@GetMapping("/signup")
	public ModelAndView paginaCadastroUsuario(User user) {
		ModelAndView mv = new ModelAndView("add-user");

		mv.addObject("assinaturas", assinaturaRepository.findAll());
		return mv;
	}

	@GetMapping("/")
	public ModelAndView paginaInicial(User user) {

		ModelAndView mv = new ModelAndView("index");
		mv.addObject("users", userRepository.findAll());
		mv.addObject("assinaturas", assinaturaRepository.findAll());
		return mv;
	}

//    
	@PostMapping("/adduser")
	public ModelAndView salvarUsuario(User user) {

		userRepository.save(user);

		ModelAndView mv = new ModelAndView("redirect:/");

		return mv;
	}

	@GetMapping("/edit/{id}")
	public ModelAndView paginaAtualizarUsuario(@PathVariable("id") Long id, Model model) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		ModelAndView mv = new ModelAndView("update-user");

		mv.addObject("assinaturas", assinaturaRepository.findAll());
		mv.addObject("user", user);

		return mv;
	}

	@PostMapping("/update/{id}")
	public ModelAndView atualizarUsuario(@PathVariable("id") long id, User user) {

		userRepository.save(user);
		ModelAndView mv = new ModelAndView("redirect:/");

		mv.addObject("users", userRepository.findAll());
		return mv;
	}

	@GetMapping("/delete/{id}")
	public String paginaApagarUsuario(@PathVariable("id") long id, Model model) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		userRepository.delete(user);
		model.addAttribute("users", userRepository.findAll());
		return "index";
	}

	// CADASTRO DE ASSINATRUAS
	@GetMapping("/cadastroAssinatura")
	public ModelAndView paginaCadastroAssinatura(Assinatura assinatura) {
		ModelAndView mv = new ModelAndView("add-assinatura");
		return mv;
	}

	@PostMapping("/addAssinatura")
	public ModelAndView salvarAssinatura(Assinatura assinatura) {

		assinaturaRepository.save(assinatura);

		ModelAndView mv = new ModelAndView("redirect:/");

		return mv;
	}

	@GetMapping("/assinatura/edit/{id}")
	public ModelAndView paginaAtualizarAssinatura(@PathVariable("id") Long id) {
		Assinatura assinatura = assinaturaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		ModelAndView mv = new ModelAndView("update-assinatura");
		mv.addObject("assinatura", assinatura);

		return mv;
	}

	@PostMapping("/assinatura/update/{id}")
	public ModelAndView atualizarAssinatura(@PathVariable("id") long id, Assinatura assinatura) {

		assinaturaRepository.save(assinatura);
		ModelAndView mv = new ModelAndView("redirect:/");

		return mv;
	}

	@GetMapping("/assinatura/delete/{id}")
	public ModelAndView ApagarAssinatura(@PathVariable("id") long id) {
		Assinatura assinatura = assinaturaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		assinaturaRepository.delete(assinatura);
		ModelAndView mv = new ModelAndView("redirect:/");

		return mv;
	}
}
