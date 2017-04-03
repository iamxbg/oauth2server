/* 连接过期的跳转函数 */
function sessionOut(){
				alert('连接过期!请重新登录!')
				
				var parentBody=window.parent.document.getElementsByTagName('body')[0];

				var out = parentBody.ownerDocument.createElement('form');
				  			out.action='login/quit';
				  			out.method='GET';
				  			
				  		parentBody.appendChild(out);
				  			out.submit();

			}

