package JavaSpider.main;

import JavaSpider.bean.Imooc;

public class Main {

	public static void main(String[] args) {
		String url = "http://www.imooc.com/wenda/detail/351144";
		Imooc imooc;

		for(int i=0; i<3;i++){
			imooc = new Imooc(url);
			url = imooc.nextUrl;
			System.out.println(imooc);
		}
	}
}
