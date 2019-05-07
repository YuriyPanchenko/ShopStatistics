<#macro addNewGoods>
    <div  class="container">
        <h2>Add new goods</h2>

        <form method="post">
            <div class="container">
                <div class="row">
                    <div >
                        <input class="form-control" type="text" name="name" placeholder="Name of goods" required/>
                    </div>
                    <div >
                        <input class="form-control" type="number" name="price" placeholder="price" required>
                    </div>
                    <div >
                        <input  class="form-control" type="date" name="date" placeholder="date" required>
                    </div>
                    <div>
                        <select class="form-control" name="currency" required>
                            <option>UAH</option>
                            <option>USD</option>
                            <option>EUR</option>
                            <option>PLN</option>
                        </select>
                    </div>
                </div>

            </div>
            <div>
                <button type="submit">add</button>
            </div>
        </form>

    </div>
</#macro>

<#macro link workLink nameLink>
    <div>
        <a href="${workLink}">${nameLink}</a>
    </div>
</#macro>

<#macro showGoods listOfGoods>

    <#list listOfGoods as current>
        <div class="container align" style="background: #e5eeee; margin-top: 30px;">
            <div class="row">Date: ${current.date}</div>
            <div class="row">Price: ${current.price} ${current.currency}</div>
            <div class="row">Name: ${current.name}</div>
        </div>
    <#else>
    List is empty
    </#list>

</#macro>
<#macro totalProfit>
<div class="container">
    <form method="post" action="/totalYearProfit" >
        <div class="container">
            <div class="row">
                <div>
                    <input class="form-control" type="number" name="year" placeholder="year" required>
                </div>
                <div>
                    <select class="form-control" name="currency" required>
                        <option>UAH</option>
                        <option>USD</option>
                        <option>EUR</option>
                        <option>PLN</option>
                    </select>
                </div>
                <div>
                    <button class="form-control" type="submit">Show total profit</button>
                </div>
            </div>
        </div>

    </form>
</div>
</#macro>

<#macro findGoods>
    <div  class="container">
        <form method="post">
            <div class="container">
                <div class="row">
                    <div>
                        <input class="form-control" type="date" name="date" placeholder="date" required>
                    </div>
                    <div>
                        <button class="form-control" type="submit">Find by date</button>
                    </div>
                </div>
            </div>
        </form>

    </div>
    <#--<form method="post" action="/findGoods">
        <input type="date" name="date" placeholder="date" required>
        <button type="submit">Find by date</button>
    </form>-->
</#macro>