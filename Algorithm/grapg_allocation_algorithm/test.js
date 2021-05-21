var init_arr        = [];   //记录初始下标
var index_arr       = [];   //记录有效下标
var arr_length      = 25;   //数组长度
var grid_length     = 5;    //网格长度

var model_square    = [];   //正方形模型
var model_trangle   = [];   //三角形模型
var model_rectangle = [];   //长方形模型

var square_arr      = [];   //正方形模型数据
var trangle_arr     = [];   //三角形模型数据
var rectangle_arr   = [];   //长方形模型数据
var single_arr      = [];   //存储单个数据


/**
 * 初始化函数
 */
function init()
{
    console.log('初始化中');
    init_arr.length     = 0;
    index_arr.length    = 0;
    for(let i = 0; i < arr_length; i++)
    {
        init_arr.push(i);
        index_arr.push(0);
    }
}


/**
 * 检测数组下标是否被占用
 */
function check_blank_index(index)
{
    return index_arr[index] != 0 ? true : false;
}


/**
 * 获取未被占用过的下标
 */
function get_blank_index()
{
    let index = random(0,init_arr.length-1);
    
    if(check_blank_index(index))
    {
        
        return get_blank_index();
    }
    else
    {
        return index;
    }
    
}


/**
 * 获取随机数
 */
function random(minNum,maxNum)
{ 
    switch(arguments.length){ 
        case 1: 
            return parseInt(Math.random()*minNum+1,10); 
        break; 
        case 2: 
            return parseInt(Math.random()*(maxNum-minNum+1)+minNum,10); 
        break; 
        default: 
            return 0; 
        break; 
    } 
} 


/**
 * 获取方向坐标
 */
function get_direction_index(index,direction)
{
    switch(direction)
    {
        case 'up':
            return index-grid_length < 0 ? -1 : index-grid_length;
        break;
        case 'down':
            return index+grid_length > arr_length-1 ? -1 : index+grid_length;
        break;
        case 'left':
            return index % grid_length == 0 ? -1 : index-1;
        break;
        case 'right':
            return index % grid_length == 4 ? -1 : index+1;
        break;
        case 'up_left':
            return index % grid_length == 0 || index-grid_length < 0 ? -1 : index-grid_length-1;
        break;
        case 'up_right':
            return index % grid_length == 4 || index-grid_length < 0 ? -1 : index-grid_length+1;
        break;
        case 'down_left':
            return index % grid_length == 0 || index+grid_length > arr_length-1 ? -1 : index+grid_length-1;
        break;
        case 'down_right':
            return index % grid_length == 4 || index+grid_length > arr_length-1 ? -1 : index+grid_length+1;
        break;
        default:
            console.log("方向选择错误");
    }
}


/**
 * 构建模型
 */
function build_model()
{
    model_square.push(['up','left','up_left']);
    model_square.push(['up','right','up_right']);
    model_square.push(['down','left','down_left']);
    model_square.push(['down','right','down_right']);

    model_trangle.push(['up','left']);
    model_trangle.push(['up','right']);
    model_trangle.push(['down','left']);
    model_trangle.push(['down','right']);

    model_rectangle.push(['up','down']);
    model_rectangle.push(['left','right']);
}


/**
 * 数字化模型
 * @returns 
 */
function get_model_data(arr,model)
{
    arr.length = 0;
    let blank_index = get_blank_index();
    let type = random(0,model.length-1);


    for(let i = 0 ; i < model[type].length; i++)
    {
        let index_value = get_direction_index(blank_index, model[type][i]);

        if(index_value == -1 || check_blank_index(index_value))
        {
            //console.log("有冲突");
            get_model_data(arr,model);
            return;
        }
        else
        {
            arr.push(index_value);
            arr.length == model[type].length ? arr.push(blank_index) : null;
        }

    }
}


/**
 *  获取单个数据 
 */
function get_single_data(arr)
{
    single_arr.length = 0;
    let index_value = get_blank_index();
    arr.push(index_value);
}


/**
 * 标注已分配好的下标
 */
function indicia_arr(arr)
{
    for(var i = 0; i < arr.length; i++)
    {
        !check_blank_index(arr[i]) ? index_arr[arr[i]] = 1 : console.log("标注出错");
    }
}


/**
 * 分配
 */
function allocation()
{
    init();
    build_model();

    get_model_data(square_arr,model_square);
    indicia_arr(square_arr);
    
    get_model_data(trangle_arr,model_trangle);
    indicia_arr(trangle_arr);

    get_model_data(rectangle_arr,model_rectangle);
    indicia_arr(rectangle_arr);

    get_single_data(single_arr);
    indicia_arr(single_arr);

    let data = {
        'square_arr'    : square_arr,
        'trangle_arr'   : trangle_arr,
        'rectabgle_arr' : rectangle_arr,
        'single_arr'    : single_arr
    }

    console.log(data);
    return data;
}


allocation();