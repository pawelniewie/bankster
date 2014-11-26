module MBank

	module Pages

		class AccountSection < SitePrism::Section
			element :name, 'li.product-name span.value'
			element :number, 'li.number'
			element :balance, 'li.balance span.amount'
			element :currency, 'li.balance span.currency'
			element :available, 'li.available-balance span.amount'
			element :history_link, 'span.buttons a[data-action-link="GetAccountHistory"]'
		end

		class AccountsSelectorSection < SitePrism::Section
			element :selector, '#accounts-selector .selected-account'
			elements :accounts, '.accounts-selector-profile ul.accounts-list li a'

			def open_accounts
				selector.hover
			end

			def get_account_names
				open_accounts
				accounts.map { |account| account.text }
			end

			def select_account(account_name)
				open_accounts
				accounts.find { |account| account.text == account_name }.click
			end
		end

		class DesktopPage < SitePrism::Page
			sections :accounts, AccountSection, '#desktop-accounts-list li.operation-row'
			section :accounts_selector, AccountsSelectorSection, '#accounts-selector'
			element :loading_icon, '.loading-icon'

			def account_names
				accounts.map { |account| account.name.text }
			end
		end

		class LoginPage < SitePrism::Page
			set_url "https://online.mbank.pl/"

			element :user_id, '#userID'
			element :password, '#pass'
			element :submit, '#submitButton'

			def login(user_id, password)
				wait_for_user_id
				self.user_id.set user_id
				self.password.set password
				self.submit.click
				@desktop = DesktopPage.new
			end
		end
	end

	def self.download_history(user_id, password)
		login = MBank::Pages::LoginPage.new()
		login.load()
		desktop = login.login(user_id, password)
		desktop.wait_for_accounts_selector
		accounts = desktop.accounts_selector.get_account_names

		accounts.each do |account| 
			desktop = MBank::Pages::DesktopPage.new
			desktop.wait_for_accounts_selector
			desktop.accounts_selector.select_account(account)

			desktop = MBank::Pages::DesktopPage.new
			desktop.wait_for_loading_icon
			desktop.wait_until_loading_icon_visible
			
			desktop = MBank::Pages::DesktopPage.new
			desktop.wait_for_accounts
			registers = desktop.account_names
			
		end
	end
end